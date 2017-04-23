package com.rdfs.framework.cache.service;

import java.util.List;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.entity.CwTaskMonitor;
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
	 * 根据节点ID获取节点事件
	 * @param nodeId
	 * @return
	 */
	List<CwNodeEvent> getNodeEventList(Integer nodeId);

	/**
	 * 根据节点事件ID获取监听
	 * @param eventId
	 * @return
	 */
	List<CwTaskMonitor> getTaskMonitorList(Integer eventId);

	/**
	 * 缓存流程
	 * @param processInfo
	 */
	void cacheProcessInfo(CwProcessInfo processInfo);

	/**
	 * 删除流程
	 * @param processInfo
	 */
	void delProcessInfo(CwProcessInfo processInfo);
	
	/**
	 * 更新流程
	 * @param processInfo
	 */
	void updateProcessInfo(CwProcessInfo processInfo);
	
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

	/**
	 * 缓存节点事件
	 * @param nodeEvent
	 */
	void cacheNodeEvent(CwNodeEvent nodeEvent);

	/**
	 * 删除节点事件
	 * @param nodeEvent
	 */
	void delNodeEvent(CwNodeEvent nodeEvent);

	/**
	 * 缓存事件监听
	 * @param taskMonitor
	 */
	void cacheTaskMonitor(CwTaskMonitor taskMonitor);

	/**
	 * 删除事件监听
	 * @param taskMonitor
	 */
	void delTaskMonitor(CwTaskMonitor taskMonitor);

	/**
	 * 按事件节点删除监听
	 * @param nodeEvent
	 */
	void delTaskMonitor(CwNodeEvent nodeEvent);

	/**
	 * 按事件监听缓存监听
	 * @param nodeEvent
	 */
	void cacheTaskMonitor(CwNodeEvent nodeEvent);

}
