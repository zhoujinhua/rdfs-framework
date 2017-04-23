package com.rdfs.framework.cache.service;

import java.util.List;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.entity.CwTaskNode;

public interface CacheWorkflowService extends HibernateService {

	/**
	 * 缓存流程数据
	 */
	void cacheWorkflowData();

	/**
	 * 从缓存中获取流程数据
	 * @return
	 */
	List<CwProcessInfo> getProcessInfos();

	/**
	 * 根据流程ID获取流程
	 * @param id
	 * @return
	 */
	CwProcessInfo getProcessInfo(Integer id);

	/**
	 * 根据流程ID获取流程节点
	 * @param flowId
	 * @return
	 */
	List<CwTaskNode> getTaskNodeList(Integer flowId);

	/**
	 * 缓存流程
	 * @param processInfo
	 */
	void cacheProcessInfo(CwProcessInfo processInfo);

	/**
	 * 缓存节点
	 * @param taskNode
	 */
	void cacheTaskNode(CwTaskNode taskNode);

	/**
	 * 删除节点
	 * @param taskNode
	 */
	void delTaskNode(CwTaskNode taskNode);

}
