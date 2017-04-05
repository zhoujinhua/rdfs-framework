package com.rdfs.framework.workflow.service.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.service.TreeService;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.entity.CwTaskNode;
import com.rdfs.framework.workflow.service.TaskNodeService;


@Service
public class TaskNodeServiceImpl extends HibernateServiceSupport implements TaskNodeService {

	@Autowired
	private TreeService treeService;
	
	@Override
	public CwTaskNode getStartNode(CwProcessInfo processInfo) {
		List<CwTaskNode> list = processInfo.getTaskNodes();
		if(list!=null && !list.isEmpty()){
			for(CwTaskNode taskNode : list){
				if(taskNode.getType().equals("start")){
					return taskNode;
				}
			}
		}
		return null;
	}

	@Override
	public CwTaskNode getEndNode(CwProcessInfo processInfo) {
		List<CwTaskNode> list = processInfo.getTaskNodes();
		if(list!=null && !list.isEmpty()){
			for(CwTaskNode taskNode : list){
				if(taskNode.getType().equals("end")){
					return taskNode;
				}
			}
		}
		return null;
	}

	@Override
	public void saveTaskNode(CwTaskNode taskNode) {
		String hql = "from CwTaskNode where processInfo.id = "+taskNode.getProcessInfo().getId()+" and code = '" + taskNode.getCode() + "'";
		if(taskNode.getType().equals("01") || taskNode.getType().equals("03")){
			hql += " or type = '" + taskNode.getType() + "'";
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
		deleteEntity(taskNode);
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
