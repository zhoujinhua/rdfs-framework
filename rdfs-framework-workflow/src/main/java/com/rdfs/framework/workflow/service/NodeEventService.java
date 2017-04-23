package com.rdfs.framework.workflow.service;

import java.util.List;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwTaskNode;

public interface NodeEventService extends HibernateService{

	/**
	 * 根据任务节点+动作找到指定的节点事件
	 * @param treeNode 任务节点
	 * @param action 事件
	 * @return
	 */
	CwNodeEvent getNodeEvent(CwTaskNode taskNode, String action);

	void deleteNodeEvent(CwNodeEvent nodeEvent);

	void saveNodeEvent(CwNodeEvent nodeEvent);

	List<CwNodeEvent> getNodeEventList(Integer id);

}
