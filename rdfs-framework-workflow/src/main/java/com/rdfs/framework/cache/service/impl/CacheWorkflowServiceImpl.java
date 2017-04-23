package com.rdfs.framework.cache.service.impl;

import java.util.Iterator;
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
import com.rdfs.framework.workflow.entity.CwProcessInfo;
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
		List<CwProcessInfo> processInfos = processInfoService.getList("from CwProcessInfo a ");
		Jedis jedis = JedisUtil.getJedisClient();
		if(processInfos!=null && !processInfos.isEmpty()){
			Map<String, String> nodeMap = Maps.newHashMap();
			for(CwProcessInfo processInfo : processInfos){
				jedis.hset(Constants.KEYS.PROCESS_KEY, processInfo.getId()+"", JacksonUtil.toJson(processInfo));
				Hibernate.initialize(processInfo.getTaskNodes());
				if(processInfo.getTaskNodes()!=null && !processInfo.getTaskNodes().isEmpty()){
					nodeMap.put(processInfo.getId()+"", JacksonUtil.toJson(processInfo.getTaskNodes()));
				}
			}
			jedis.hmset(Constants.KEYS.PROCESS_NODE_KEY, nodeMap);
			JedisUtil.close(jedis);
		}
	}
	
	@Override
	public List<CwProcessInfo> getProcessInfos(){
		List<CwProcessInfo> processInfoList = Lists.newArrayList();
		Map<String, CwProcessInfo> map = Maps.newHashMap();
		Jedis jedis = JedisUtil.getJedisClient();
		List<String> codeList = jedis.hvals(Constants.KEYS.PROCESS_KEY);
		JedisUtil.close(jedis);
		if (RdfsUtils.isEmpty(codeList)) {
			logger.error(RdfsUtils.merge("没有获取到流程列表"));
		}else {
			for (String code : codeList) {
				if(!StringUtils.isBlank(code)){
					CwProcessInfo processInfo = (CwProcessInfo)JacksonUtil.fromJson(code, CwProcessInfo.class);
					if(processInfo!=null && map.get(processInfo.getCode())!=null){
						if(map.get(processInfo.getCode()).getVersion() < processInfo.getVersion()){
							map.put(processInfo.getCode(), processInfo);
						}
					}
				}
			}
		}
		for(Iterator<String> iter = map.keySet().iterator();iter.hasNext();){
			String key = iter.next();
			processInfoList.add(map.get(key));
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
				cacheTaskNode(taskNode);
			}
		}
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.PROCESS_KEY, processInfo.getId()+"", JacksonUtil.toJson(processInfo));
		jedis.close();
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
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.PROCESS_NODE_KEY, taskNode.getProcessInfo().getId()+"", JacksonUtil.toJson(taskNodeList));
		jedis.close();
	}
	
}
