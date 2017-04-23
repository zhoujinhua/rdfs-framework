package com.rdfs.framework.workflow.service;

import java.util.List;

import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.entity.CwTaskNode;

public interface TaskNodeService extends HibernateService {

	/**
	 * 获取流程定义的起始节点
	 * @param processInfo 流程定义
	 * @return
	 */
	CwTaskNode getStartNode(CwProcessInfo processInfo);

	/**
	 * 获取流程定义的结束节点
	 * @param processInfo 流程定义
	 * @return
	 */
	CwTaskNode getEndNode(CwProcessInfo processInfo);

	void saveTaskNode(CwTaskNode taskNode);

	void deleteTaskNode(CwTaskNode taskNode);

	List<TreeDto> nodeTree(CwTaskNode taskNode) throws Exception;

	List<CwTaskNode> getTaskNodeList(Integer id);
}
