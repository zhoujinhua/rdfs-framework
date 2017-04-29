package com.rdfs.framework.workflow.service.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rdfs.framework.cache.service.CacheWorkflowService;
import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.service.TreeService;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.constant.Constants;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.entity.CwTaskNode;
import com.rdfs.framework.workflow.service.TaskNodeService;


@Service
public class TaskNodeServiceImpl extends HibernateServiceSupport implements TaskNodeService {
	
	@Autowired
	private CacheWorkflowService cacheWorkflowService;
	
	@Autowired
	private TreeService treeService;
	
	@Override
	public CwTaskNode getStartNode(CwProcessInfo processInfo) {
		List<CwTaskNode> list = cacheWorkflowService.getTaskNodeList(processInfo.getId());
		if(list==null || list.isEmpty()){
			list = getList("from CwTaskNode where processInfo.id = " + processInfo.getId());
		}
		
		if(list!=null && !list.isEmpty()){
			for(CwTaskNode taskNode : list){
				if(taskNode.getType().equals(Constants.NODE_TYPE.START)){
					return taskNode;
				}
			}
		}
		return null;
	}

	@Override
	public CwTaskNode getEndNode(CwProcessInfo processInfo) {
		List<CwTaskNode> list = cacheWorkflowService.getTaskNodeList(processInfo.getId());
		if(list==null || list.isEmpty()){
			list = getList("from CwTaskNode where processInfo.id = " + processInfo.getId());
		}
		
		if(list!=null && !list.isEmpty()){
			for(CwTaskNode taskNode : list){
				if(taskNode.getType().equals(Constants.NODE_TYPE.END)){
					return taskNode;
				}
			}
		}
		return null;
	}

	@Override
	public void saveTaskNode(CwTaskNode taskNode) {
		String hql = "from CwTaskNode where processInfo.id = "+taskNode.getProcessInfo().getId();
		if(taskNode.getType().equals(Constants.NODE_TYPE.START) || taskNode.getType().equals(Constants.NODE_TYPE.END)){
			hql += " and (code = '" + taskNode.getCode() +"' or type = '" + taskNode.getType() + "')";
		} else {
			hql += " and code = '" + taskNode.getCode() + "'";
		}
		List<CwTaskNode> nodeList = getList(hql);
		if(taskNode.getId()!=null){
			if(nodeList!=null && !nodeList.isEmpty()){
				if(nodeList.size()>1 || nodeList.get(0).getId().intValue() != taskNode.getId().intValue()){
					throw new RuntimeException("节点代码重复或者该流程已存在开始(结束)标识.");
				}
			}
			updateEntity(taskNode, "name","code","type");
		}else {
			if(nodeList!=null && !nodeList.isEmpty()){
				throw new RuntimeException("节点代码重复或者该流程已存在开始(结束)标识.");
			}
			saveEntity(taskNode);
		}
		cacheWorkflowService.cacheTaskNode(taskNode);
	}

	@Override
	public void deleteTaskNode(CwTaskNode taskNode) {
		taskNode = getEntityById(CwTaskNode.class, taskNode.getId(), true);
		List<CwNodeEvent> nodeEvents = taskNode.getNodeEvents();
		if(nodeEvents!=null && !nodeEvents.isEmpty()){
			for(CwNodeEvent nodeEvent : nodeEvents){
				Hibernate.initialize(nodeEvent.getTaskMonitors());
				nodeEvent.getTaskMonitors().clear();
			}
			taskNode.getNodeEvents().clear();
		}
		CwTaskNode cwTaskNode = new CwTaskNode();
		cwTaskNode.setId(taskNode.getId());
		deleteEntity(taskNode);
		cacheWorkflowService.delTaskNode(cwTaskNode);
	}
	
	@Override
	public List<TreeDto> nodeTree(CwTaskNode taskNode) throws Exception {
		taskNode = getEntityById(CwTaskNode.class, taskNode.getId(), true);
		
		CwProcessInfo processInfo = taskNode.getProcessInfo();
		Hibernate.initialize(processInfo.getTaskNodes());
		
		List<CwTaskNode> taskNodes = processInfo.getTaskNodes();
		if(taskNodes!=null && !taskNodes.isEmpty()){
			return treeService.getList(taskNodes, false, "id", "name", null, null);
		}
		return null;
	}
}
