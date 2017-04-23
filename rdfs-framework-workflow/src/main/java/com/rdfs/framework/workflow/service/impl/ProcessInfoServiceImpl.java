package com.rdfs.framework.workflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rdfs.framework.cache.service.CacheWorkflowService;
import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.entity.CwTaskMonitor;
import com.rdfs.framework.workflow.entity.CwTaskNode;
import com.rdfs.framework.workflow.service.NodeEventService;
import com.rdfs.framework.workflow.service.ProcessInfoService;
import com.rdfs.framework.workflow.service.TaskNodeService;

@Service
public class ProcessInfoServiceImpl extends HibernateServiceSupport implements ProcessInfoService {
	
	@Autowired
	private CacheWorkflowService cacheWorkflowService;
	
	@Autowired
	private TaskNodeService taskNodeService;
	
	@Autowired
	private NodeEventService nodeEventService;
	
	@Override
	public CwProcessInfo getLastProcess(String flowName){
		List<CwProcessInfo> list = getList(" from CwProcessInfo where code = '" + flowName + "' and status = '" + Constants.IS.YES + "' order by convert(version using GBK) desc");
		if(list!=null && !list.isEmpty()){
			CwProcessInfo processInfo = list.get(0);
			Hibernate.initialize(processInfo.getTaskNodes());
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<CwProcessInfo> getProcessInfos() {
		List<CwProcessInfo> list = cacheWorkflowService.getProcessInfos();
		if(list == null || list.isEmpty()){
			String hql = "from CwProcessInfo a where a.status = '" + Constants.IS.YES + "' and exists (select 1 from CwProcessInfo b where a.code = b.code group by b.code having max(b.version) = a.version)";
			return getList(hql);
		}
		return list;
	}

	@Override
	public CwProcessInfo saveProcessInfo(CwProcessInfo processInfo) {
		if(processInfo.getId()!=null){
			updateEntity(processInfo, "name", "code");
		} else {
			processInfo.setVersion(new Date().getTime());
			processInfo.setCreateTime(new Date());
			processInfo.setStatus(Constants.IS.YES);
			saveEntity(processInfo);
		}
		return processInfo;
	}

	@Override
	public CwProcessInfo updateProcessVersion(CwProcessInfo processInfo) {
		processInfo = super.getEntityById(CwProcessInfo.class, processInfo.getId(), false);
		processInfo.setVersion(new Date().getTime());
		
		return super.getEntityById(CwProcessInfo.class, processInfo.getId(), false);
	}
	
	@Override
	@SuppressWarnings("null")
	public CwProcessInfo getEntityById(CwProcessInfo processInfo){
		processInfo = getEntityById(CwProcessInfo.class, processInfo.getId(), true);
		if(processInfo == null){
			processInfo = super.getEntityById(CwProcessInfo.class, processInfo.getId(), true);
			List<CwTaskNode> nodeList = processInfo.getTaskNodes();
			if(nodeList!=null && !nodeList.isEmpty()){
				for(CwTaskNode taskNode : nodeList){
					Hibernate.initialize(taskNode.getNodeEvents());
					if(taskNode.getNodeEvents()!=null && !taskNode.getNodeEvents().isEmpty()){
						for(CwNodeEvent nodeEvent : taskNode.getNodeEvents()){
							Hibernate.initialize(nodeEvent.getTaskMonitors());
						}
					}
				}
			}
		} else { //从缓存取
			List<CwTaskNode> nodeList = cacheWorkflowService.getTaskNodeList(processInfo.getId());
			if(nodeList!=null && !nodeList.isEmpty()){
				for(CwTaskNode taskNode : nodeList){
					List<CwNodeEvent> eventList = cacheWorkflowService.getNodeEventList(taskNode.getId());
					if(eventList!=null && !eventList.isEmpty()){
						for(CwNodeEvent nodeEvent : eventList){
							List<CwTaskMonitor> monitorList = cacheWorkflowService.getTaskMonitorList(nodeEvent.getId());
							nodeEvent.setTaskMonitors(monitorList);
						}
						taskNode.setNodeEvents(eventList);
					}
				}
				processInfo.setTaskNodes(nodeList);
			}
		}
		return processInfo;
	}
	
	@Override
	public List<TreeDto> nodeTree(CwProcessInfo processInfo) throws Exception {
		List<TreeDto> treeList = new ArrayList<>();
		
		processInfo = getEntityById(CwProcessInfo.class, processInfo.getId(), true);
		TreeDto treeDto = new TreeDto(processInfo.getId()+"", "0", processInfo.getName(), true, true);
		treeList.add(treeDto);
		
		List<CwTaskNode> nodeList = taskNodeService.getTaskNodeList(processInfo.getId());
		if(nodeList!=null && !nodeList.isEmpty()){
			for(CwTaskNode taskNode : nodeList){
				String nodeId = "node-"+taskNode.getId();
				treeDto = new TreeDto(nodeId, processInfo.getId()+"", taskNode.getName(),true);
				treeList.add(treeDto);
				
				List<CwNodeEvent> eventList = nodeEventService.getNodeEventList(taskNode.getId());
				if(eventList!=null && !eventList.isEmpty()){
					for(CwNodeEvent nodeEvent : eventList){
						String eventId = "event-"+nodeEvent.getId();
						treeDto = new TreeDto(eventId, nodeId, nodeEvent.getName(), false);
						treeList.add(treeDto);
					}
				}
			}
		}
		
		return treeList;
	}

	@Override
	public CwProcessInfo saveCopyProcess(CwProcessInfo processInfo) {
		processInfo.setId(null);
		List<CwTaskNode> nodeList = processInfo.getTaskNodes();
		processInfo.setTaskNodes(null);
		processInfo.setVersion(new Date().getTime());
		processInfo.setStatus(Constants.IS.NO);
		saveEntity(processInfo);
		
		if(nodeList!=null && !nodeList.isEmpty()){
			Map<String, CwTaskNode> map = new HashMap<>();
			List<CwNodeEvent> allList = new ArrayList<>();
			for(CwTaskNode taskNode : nodeList){
				taskNode.setId(null);
				taskNode.setProcessInfo(processInfo);
				allList.addAll(taskNode.getNodeEvents());
				taskNode.setNodeEvents(null);
				saveEntity(taskNode);
				map.put(taskNode.getCode(), taskNode);
			}
			if(allList!=null && !allList.isEmpty()){
				for(CwNodeEvent nodeEvent : allList){
					nodeEvent.setId(null);
					nodeEvent.setCurrNode(map.get(nodeEvent.getCurrNode().getCode()));
					nodeEvent.setNextNode(map.get(nodeEvent.getNextNode().getCode()));
					List<CwTaskMonitor> monitorList = nodeEvent.getTaskMonitors();
					nodeEvent.setTaskMonitors(null);
					saveEntity(nodeEvent);
					
					if(monitorList!=null && !monitorList.isEmpty()){
						for(CwTaskMonitor taskMonitor : monitorList){
							taskMonitor.setId(null);
							taskMonitor.setEventId(nodeEvent.getId());
							saveEntity(taskMonitor);
						}
					}
				}
			}
		}
		return super.getEntityById(CwProcessInfo.class, processInfo.getId(), false);
	}

	@Override
	public CwProcessInfo getEntityById(Class<CwProcessInfo> clazz, Integer id, boolean flag) {
		CwProcessInfo processInfo = cacheWorkflowService.getProcessInfo(id);
		if(processInfo == null){
			return super.getEntityById(CwProcessInfo.class, id, flag);
		}
		return processInfo;
	}

	@Override
	public CwProcessInfo updateProcessStatus(CwProcessInfo processInfo) {
		processInfo = super.getEntityById(CwProcessInfo.class, processInfo.getId(), false);
		if(processInfo.getStatus()==null || Constants.IS.NO.equals(processInfo.getStatus())){
			processInfo.setStatus(Constants.IS.YES);
		} else {
			processInfo.setStatus(Constants.IS.NO);
		}
		return getLastProcess(processInfo.getCode());
	}
}
