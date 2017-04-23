package com.rdfs.framework.workflow.service.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rdfs.framework.cache.service.CacheWorkflowService;
import com.rdfs.framework.core.utils.StringUtils;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwTaskNode;
import com.rdfs.framework.workflow.service.NodeEventService;


@Service
public class NodeEventServiceImpl extends HibernateServiceSupport implements NodeEventService {

	@Autowired
	private CacheWorkflowService cacheWorkflowService;
	
	@Override
	public CwNodeEvent getNodeEvent(CwTaskNode taskNode, String action){
		List<CwNodeEvent> list = cacheWorkflowService.getNodeEventList(taskNode.getId());
		if(list!=null && !list.isEmpty()){
			for(CwNodeEvent nodeEvent : list){
				if(nodeEvent.getAction().equals(action)){
					return nodeEvent;
				}
			}
		}
		
		String hql = "from CwNodeEvent where currNode.id = " + taskNode.getId();
		if(!StringUtils.isBlank(action)){
			hql += " and action = '" + action + "'";
		} else {
			hql += " and action is null";
		}
		List<CwNodeEvent> nodeEvents = getList(hql);
		if(nodeEvents!=null && !nodeEvents.isEmpty()){
			CwNodeEvent nodeEvent = nodeEvents.get(0);
			Hibernate.initialize(nodeEvent.getNextNode());
			return nodeEvent;
		}
		return null;
	}

	@Override
	public void deleteNodeEvent(CwNodeEvent nodeEvent) {
		nodeEvent = getEntityById(CwNodeEvent.class, nodeEvent.getId(), true);
		nodeEvent.getTaskMonitors().clear();
		deleteEntity(nodeEvent);
	}

	@Override
	public void saveNodeEvent(CwNodeEvent nodeEvent) {
		if(nodeEvent.getId()!=null){
			updateEntity(nodeEvent, "name","currNode","action","nextNode","route","group","user");
		}else {
			saveEntity(nodeEvent);
		}
	}

	@Override
	public List<CwNodeEvent> getNodeEventList(Integer id) {
		List<CwNodeEvent> eventList = cacheWorkflowService.getNodeEventList(id);
		if(eventList == null || eventList.isEmpty()){
			return getList("from CwNodeEvent where currNode.id = " + id);
		}
		return null;
	}
}
