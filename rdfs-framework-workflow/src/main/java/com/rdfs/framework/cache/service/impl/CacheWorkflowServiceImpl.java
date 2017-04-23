package com.rdfs.framework.cache.service.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rdfs.framework.cache.service.CacheWorkflowService;
import com.rdfs.framework.core.redis.JedisUtil;
import com.rdfs.framework.core.utils.RdfsUtils;
import com.rdfs.framework.core.utils.StringUtils;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.hibernate.utils.JacksonUtil;
import com.rdfs.framework.workflow.constant.Constants;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.entity.CwTaskMonitor;
import com.rdfs.framework.workflow.entity.CwTaskNode;
import com.rdfs.framework.workflow.service.ProcessInfoService;

import redis.clients.jedis.Jedis;

@Service
public class CacheWorkflowServiceImpl extends HibernateServiceSupport implements CacheWorkflowService{

	@Autowired
	private ProcessInfoService processInfoService;
	
	private Logger logger = LoggerFactory.getLogger(CacheWorkflowServiceImpl.class);
	
	@Override
	public void cacheWorkflowData(){
		List<CwProcessInfo> processInfos = processInfoService.getList("from CwProcessInfo a where a.status = '" + com.rdfs.framework.core.contants.Constants.IS.YES + "' and exists (select 1 from CwProcessInfo b where a.code = b.code group by b.code having max(b.version) = a.version)");
		Jedis jedis = JedisUtil.getJedisClient();
		if(processInfos!=null && !processInfos.isEmpty()){
			Map<String, String> nodeMap = Maps.newHashMap();
			Map<String, String> eventMap = Maps.newHashMap();
			Map<String, String> monitorMap = Maps.newHashMap();
			for(CwProcessInfo processInfo : processInfos){
				jedis.hset(Constants.KEYS.PROCESS_KEY, processInfo.getId()+"", JacksonUtil.toJson(processInfo));
				Hibernate.initialize(processInfo.getTaskNodes());
				if(processInfo.getTaskNodes()!=null && !processInfo.getTaskNodes().isEmpty()){
					nodeMap.put(processInfo.getId()+"", JacksonUtil.toJson(processInfo.getTaskNodes()));
					for(CwTaskNode taskNode : processInfo.getTaskNodes()){
						Hibernate.initialize(taskNode.getNodeEvents());
						if(taskNode.getNodeEvents()!=null && !taskNode.getNodeEvents().isEmpty()){
							eventMap.put(taskNode.getId()+"", JacksonUtil.toJson(taskNode.getNodeEvents()));
							for(CwNodeEvent nodeEvent : taskNode.getNodeEvents()){
								if(nodeEvent.getTaskMonitors()!=null && !nodeEvent.getTaskMonitors().isEmpty()){
									monitorMap.put(nodeEvent.getId()+"", JacksonUtil.toJson(nodeEvent.getTaskMonitors()));
								}
							}
						}
					}
				}
			}
			jedis.hmset(Constants.KEYS.PROCESS_NODE_KEY, nodeMap);
			jedis.hmset(Constants.KEYS.NODE_EVENT_KEY, eventMap);
			jedis.hmset(Constants.KEYS.EVENT_MONITOR_KEY, monitorMap);
			JedisUtil.close(jedis);
		}
	}
	
	@Override
	public List<CwProcessInfo> getProcessInfos(){
		List<CwProcessInfo> processInfoList = Lists.newArrayList();
		Jedis jedis = JedisUtil.getJedisClient();
		List<String> codeList = jedis.hvals(Constants.KEYS.PROCESS_KEY);
		JedisUtil.close(jedis);
		if (RdfsUtils.isEmpty(codeList)) {
			logger.error(RdfsUtils.merge("没有获取到流程列表"));
		}else {
			for (String code : codeList) {
				if(!StringUtils.isBlank(code)){
					processInfoList.add((CwProcessInfo)JacksonUtil.fromJson(code, CwProcessInfo.class));
				}
			}
		}
		return processInfoList;
	}
	
	@Override
	public CwProcessInfo getProcessInfo(Integer id){
		if (RdfsUtils.isEmpty(id)) {
			logger.error("获取流程信息失败：流程ID不能为空。");
			return null;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String proString = jedis.hget(Constants.KEYS.PROCESS_KEY, id+"");
		JedisUtil.close(jedis);
		if(!StringUtils.isBlank(proString)){
			return JacksonUtil.fromJson(proString, CwProcessInfo.class);
		}
		return null;
	}
	
	@Override
	public void cacheProcessInfo(CwProcessInfo processInfo){
		List<CwTaskNode> taskNodeList = getList("from CwTaskNode where processInfo.id = " + processInfo.getId());
		if(taskNodeList!=null && !taskNodeList.isEmpty()){
			for(CwTaskNode taskNode : taskNodeList){
				List<CwNodeEvent> nodeEventList = getNodeEventList(taskNode.getId());
				if(nodeEventList!=null && !nodeEventList.isEmpty()){
					for(CwNodeEvent nodeEvent : nodeEventList){
						List<CwTaskMonitor> taskMonitorList = getTaskMonitorList(nodeEvent.getId());
						if(taskMonitorList!=null && !taskMonitorList.isEmpty()){
							for(CwTaskMonitor taskMonitor : taskMonitorList){
								cacheTaskMonitor(taskMonitor);
							}
						}
						cacheNodeEvent(nodeEvent);
					}
				}
				cacheTaskNode(taskNode);
			}
		}
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.PROCESS_KEY, processInfo.getId()+"", JacksonUtil.toJson(processInfo));
		jedis.close();
	}
	
	@Override
	public void delProcessInfo(CwProcessInfo processInfo){
		List<CwTaskNode> taskNodeList = getTaskNodeList(processInfo.getId());
		if(taskNodeList!=null && !taskNodeList.isEmpty()){
			for(CwTaskNode taskNode : taskNodeList){
				List<CwNodeEvent> nodeEventList = getNodeEventList(taskNode.getId());
				if(nodeEventList!=null && !nodeEventList.isEmpty()){
					for(CwNodeEvent nodeEvent : nodeEventList){
						List<CwTaskMonitor> taskMonitorList = getTaskMonitorList(nodeEvent.getId());
						if(taskMonitorList!=null && !taskMonitorList.isEmpty()){
							for(CwTaskMonitor taskMonitor : taskMonitorList){
								delTaskMonitor(taskMonitor);
							}
						}
						delNodeEvent(nodeEvent);
					}
				}
				delTaskNode(taskNode);
			}
		}
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hdel(Constants.KEYS.PROCESS_KEY, processInfo.getId()+"");
		jedis.close();
	}
	
	@Override
	public void updateProcessInfo(CwProcessInfo processInfo){
		List<CwProcessInfo> list = getProcessInfos();
		if(list!=null && !list.isEmpty()){
			boolean flag = false;
			for(CwProcessInfo info : list){
				if(info.getCode().equals(processInfo.getCode())){
					if(info.getVersion() != processInfo.getVersion() && processInfo.getVersion() > info.getVersion()){
						flag = true;
						delProcessInfo(info);
						cacheProcessInfo(processInfo);
					} else {
						Jedis jedis = JedisUtil.getJedisClient();
						jedis.hset(Constants.KEYS.PROCESS_KEY, processInfo.getId()+"", JacksonUtil.toJson(processInfo));
						jedis.close();
					}
				}
			}
			if(!flag){
				cacheProcessInfo(processInfo);
			}
		} else {
			cacheProcessInfo(processInfo);
		}
	}
	
	@Override
	public List<CwTaskNode> getTaskNodeList(Integer flowId){
		List<CwTaskNode> taskNodeList = Lists.newArrayList();
		if (RdfsUtils.isEmpty(flowId)) {
			logger.error("获取流程节点失败：流程ID不能为空。");
			return taskNodeList;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String jsonValue = jedis.hget(Constants.KEYS.PROCESS_NODE_KEY, flowId+"");
		JedisUtil.close(jedis);
		if(!StringUtils.isBlank(jsonValue)){
			if(!StringUtils.isBlank(jsonValue)){
				taskNodeList = JacksonUtil.fromJson(jsonValue, new TypeReference<List<CwTaskNode>>(){});
			}
		}
		return taskNodeList;
	}
	
	@Override
	public void cacheTaskNode(CwTaskNode taskNode){
		List<CwTaskNode> taskNodeList = getTaskNodeList(taskNode.getProcessInfo().getId());
		CwTaskNode temp = null;
		if(taskNodeList!=null && !taskNodeList.isEmpty()){
			for(CwTaskNode node : taskNodeList){
				if(node.getId() == taskNode.getId()){
					temp = node;
				}
			}
			if(temp!=null){
				taskNodeList.remove(temp);
			}
		} else {
			taskNodeList = Lists.newArrayList();
		}
		taskNodeList.add(taskNode);
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.PROCESS_NODE_KEY, taskNode.getProcessInfo().getId()+"", JacksonUtil.toJson(taskNodeList));
		jedis.close();
	}
	
	@Override
	public void delTaskNode(CwTaskNode taskNode){
		List<CwTaskNode> taskNodeList = getTaskNodeList(taskNode.getProcessInfo().getId());
		CwTaskNode temp = null;
		if(taskNodeList!=null && !taskNodeList.isEmpty()){
			for(CwTaskNode node : taskNodeList){
				if(node.getId() == taskNode.getId()){
					temp = node;
				}
			}
			if(temp!=null){
				taskNodeList.remove(temp);
			}
		}
		List<CwNodeEvent> nodeEventList = getNodeEventList(taskNode.getId());
		if(nodeEventList!=null && !nodeEventList.isEmpty()){
			for(CwNodeEvent nodeEvent : nodeEventList){
				List<CwTaskMonitor> taskMonitorList = getTaskMonitorList(nodeEvent.getId());
				if(taskMonitorList!=null && !taskMonitorList.isEmpty()){
					for(CwTaskMonitor taskMonitor : taskMonitorList){
						delTaskMonitor(taskMonitor);
					}
				}
				delNodeEvent(nodeEvent);
			}
		}
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.PROCESS_NODE_KEY, taskNode.getProcessInfo().getId()+"", JacksonUtil.toJson(taskNodeList));
		jedis.close();
	}
	
	@Override
	public List<CwNodeEvent> getNodeEventList(Integer nodeId){
		List<CwNodeEvent> nodeEventList = Lists.newArrayList();
		if (RdfsUtils.isEmpty(nodeId)) {
			logger.error("获取流程节点事件失败：流程节点ID不能为空。");
			return nodeEventList;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String jsonValue = jedis.hget(Constants.KEYS.PROCESS_NODE_KEY, nodeId+"");
		JedisUtil.close(jedis);
		if(!StringUtils.isBlank(jsonValue)){
			if(!StringUtils.isBlank(jsonValue)){
				nodeEventList = JacksonUtil.fromJson(jsonValue, new TypeReference<List<CwNodeEvent>>(){});
			}
		}
		return nodeEventList;
	}
	
	@Override
	public void cacheNodeEvent(CwNodeEvent nodeEvent){
		List<CwNodeEvent> nodeEventList = getNodeEventList(nodeEvent.getCurrNode().getId());
		CwNodeEvent temp = null;
		if(nodeEventList!=null && !nodeEventList.isEmpty()){
			for(CwNodeEvent event : nodeEventList){
				if(event.getId() == nodeEvent.getId()){
					temp = event;
				}
			}
			if(temp!=null){
				nodeEventList.remove(temp);
			}
		} else {
			nodeEventList = Lists.newArrayList();
		}
		nodeEventList.add(nodeEvent);
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.NODE_EVENT_KEY, nodeEvent.getCurrNode().getId()+"", JacksonUtil.toJson(nodeEventList));
		jedis.close();
	}
	
	@Override
	public void delNodeEvent(CwNodeEvent nodeEvent){
		List<CwNodeEvent> nodeEventList = getNodeEventList(nodeEvent.getCurrNode().getId());
		CwNodeEvent temp = null;
		if(nodeEventList!=null && !nodeEventList.isEmpty()){
			for(CwNodeEvent event : nodeEventList){
				if(event.getId() == nodeEvent.getId()){
					temp = event;
				}
			}
			if(temp!=null){
				nodeEventList.remove(temp);
			}
		}
		List<CwTaskMonitor> taskMonitorList = getTaskMonitorList(nodeEvent.getId());
		if(taskMonitorList!=null && !taskMonitorList.isEmpty()){
			for(CwTaskMonitor taskMonitor : taskMonitorList){
				delTaskMonitor(taskMonitor);
			}
		}
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.NODE_EVENT_KEY, nodeEvent.getCurrNode().getId()+"", JacksonUtil.toJson(nodeEventList));
		jedis.close();
	}
	
	@Override
	public List<CwTaskMonitor> getTaskMonitorList(Integer eventId){
		List<CwTaskMonitor> monitorList = Lists.newArrayList();
		if (RdfsUtils.isEmpty(eventId)) {
			logger.error("获取流程节点事件监听失败：流程节点事件ID不能为空。");
			return monitorList;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String jsonValue = jedis.hget(Constants.KEYS.EVENT_MONITOR_KEY, eventId+"");
		JedisUtil.close(jedis);
		if(!StringUtils.isBlank(jsonValue)){
			if(!StringUtils.isBlank(jsonValue)){
				monitorList = JacksonUtil.fromJson(jsonValue, new TypeReference<List<CwTaskMonitor>>(){});
			}
		}
		return monitorList;
	}
	
	@Override
	public void cacheTaskMonitor(CwTaskMonitor taskMonitor){
		List<CwTaskMonitor> monitorList = getTaskMonitorList(taskMonitor.getEventId());
		CwTaskMonitor temp = null;
		if(monitorList!=null && !monitorList.isEmpty()){
			for(CwTaskMonitor event : monitorList){
				if(event.getId() == taskMonitor.getId()){
					temp = event;
				}
			}
			if(temp!=null){
				monitorList.remove(temp);
			}
		} else {
			monitorList = Lists.newArrayList();
		}
		monitorList.add(taskMonitor);
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.EVENT_MONITOR_KEY, taskMonitor.getEventId()+"", JacksonUtil.toJson(monitorList));
		jedis.close();
	}
	
	@Override
	public void delTaskMonitor(CwTaskMonitor taskMonitor){
		List<CwTaskMonitor> monitorList = getTaskMonitorList(taskMonitor.getEventId());
		CwTaskMonitor temp = null;
		if(monitorList!=null && !monitorList.isEmpty()){
			for(CwTaskMonitor event : monitorList){
				if(event.getId() == taskMonitor.getId()){
					temp = event;
				}
			}
			if(temp!=null){
				monitorList.remove(temp);
			}
		}
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.EVENT_MONITOR_KEY, taskMonitor.getEventId()+"", JacksonUtil.toJson(monitorList));
		jedis.close();
	}
	
	@Override
	public void delTaskMonitor(CwNodeEvent nodeEvent){
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hdel(Constants.KEYS.EVENT_MONITOR_KEY, nodeEvent.getId()+"");
		jedis.close();
	}
	
	@Override
	public void cacheTaskMonitor(CwNodeEvent nodeEvent){
		List<CwTaskMonitor> list = getList("from CwTaskMonitor where eventId = " + nodeEvent.getId());
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.EVENT_MONITOR_KEY, nodeEvent.getId()+"", JacksonUtil.toJson(list));
		jedis.close();
	}
}
