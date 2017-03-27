package com.rdfs.framework.workflow.service.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.rdfs.framework.core.utils.StringUtils;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwTaskNode;
import com.rdfs.framework.workflow.service.NodeEventService;


@Service
public class NodeEventServiceImpl extends HibernateServiceSupport implements NodeEventService {

	@Override
	public CwNodeEvent getNodeEvent(CwTaskNode taskNode, String action){
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
}
