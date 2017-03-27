package com.rdfs.framework.workflow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.entity.CwTaskNode;
import com.rdfs.framework.workflow.service.TaskNodeService;


@Service
public class TaskNodeServiceImpl extends HibernateServiceSupport implements TaskNodeService {

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

}
