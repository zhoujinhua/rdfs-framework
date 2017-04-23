package com.rdfs.framework.workflow.service;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwProcessInfo;

public interface WorkflowService extends HibernateService{

	/**
	 * 根据流程Id获取流程定义
	 * @param id
	 * @return
	 */
	CwProcessInfo getProcessInfo(Integer id);

	/**
	 * 获取下一状态
	 * @param businessKey 业务主键
	 * @param flowId 流程ID
	 * @param action 执行动作
	 * @throws Exception
	 */
	String getNextRoute(String businessKey, Integer flowId, String action);

	/**
	 * 领单
	 * @param businessKey 业务主键
	 * @param flowId 流程Id
	 * @param userId 领单人
	 */
	void claimTask(String businessKey, Integer flowId, String userId);

	/**
	 * 启动或完成任务
	 * @param businessKey 业务主键
	 * @param flowId 流程ID
	 * @param action 执行动作
	 * @throws Exception
	 */
	void completeTask(String businessKey, Integer flowId, String action);

}
