package com.rdfs.framework.workflow.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.rdfs.framework.workflow.service.ProcessInfoService;

@Service
public class ProcessInfoServiceImpl extends HibernateServiceSupport implements ProcessInfoService {

	@Autowired
	private CacheWorkflowService cacheWorkflowService;
	
	@Override
	public List<CwProcessInfo> getProcessInfos(CwProcessInfo processInfo) {
		List<CwProcessInfo> list = cacheWorkflowService.getProcessInfos();
		if(list == null || list.isEmpty()){
			String hql = "from CwProcessInfo a where a.status = '" + Constants.IS.YES + "' ";
			if(!StringUtils.isBlank(processInfo.getType())){
				hql += " and a.type = '" + processInfo.getType() + "'";
			}
			hql += " and exists (select 1 from CwProcessInfo b where a.code = b.code group by b.code having max(b.version) = a.version)";
			return getList(hql);
		}
		return list;
	}

	@Override
	public void saveProcessInfo(CwProcessInfo processInfo) {
		if(processInfo.getId()!=null){
			updateEntity(processInfo, "name", "code");
		} else {
			processInfo.setVersion(new Date().getTime());
			processInfo.setCreateTime(new Date());
			processInfo.setStatus(Constants.IS.YES);
			saveEntity(processInfo);
		}
		cacheWorkflowService.cacheProcessInfo(processInfo);
	}

	@Override
	public void updateProcessVersion(CwProcessInfo processInfo) {
		processInfo = super.getEntityById(CwProcessInfo.class, processInfo.getId(), false);
		processInfo.setVersion(new Date().getTime());
		cacheWorkflowService.cacheProcessInfo(processInfo);
	}
	
	@Override
	public CwProcessInfo getEntityById(CwProcessInfo processInfo){
		processInfo = getEntityById(CwProcessInfo.class, processInfo.getId(), true);
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
		return processInfo;
	}
	
	@Override
	public CwProcessInfo getEntityById(Integer id){
		CwProcessInfo processInfo  = cacheWorkflowService.getProcessInfo(id);
		if(processInfo == null){
			return getEntityById(CwProcessInfo.class, id, false);
		}
		return processInfo;
	}
	
	@Override
	public List<TreeDto> nodeTree(CwProcessInfo processInfo) throws Exception {
		List<TreeDto> treeList = new ArrayList<>();
		
		processInfo = getEntityById(CwProcessInfo.class, processInfo.getId(), true);
		TreeDto treeDto = new TreeDto(processInfo.getId()+"", "0", processInfo.getName(), true, true);
		treeList.add(treeDto);
		
		Hibernate.initialize(processInfo.getTaskNodes());
		List<CwTaskNode> nodeList = processInfo.getTaskNodes();
		if(nodeList!=null && !nodeList.isEmpty()){
			for(CwTaskNode taskNode : nodeList){
				String nodeId = "node-"+taskNode.getId();
				treeDto = new TreeDto(nodeId, processInfo.getId()+"", taskNode.getName(),true);
				treeList.add(treeDto);
				
				Hibernate.initialize(taskNode.getNodeEvents());
				List<CwNodeEvent> eventList = taskNode.getNodeEvents();
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
	public void saveCopyProcess(CwProcessInfo processInfo) {
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
		cacheWorkflowService.cacheProcessInfo(processInfo);
	}

	@Override
	public void updateProcessStatus(CwProcessInfo processInfo) {
		processInfo = getEntityById(CwProcessInfo.class, processInfo.getId(), false);
		if(processInfo.getStatus()==null || Constants.IS.NO.equals(processInfo.getStatus())){
			processInfo.setStatus(Constants.IS.YES);
		} else {
			processInfo.setStatus(Constants.IS.NO);
		}
		cacheWorkflowService.cacheProcessInfo(processInfo);
	}
}
