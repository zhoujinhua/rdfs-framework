package com.rdfs.framework.workflow.service;

import com.rdfs.framework.hibernate.service.HibernateService;

public interface WorkflowService extends HibernateService{

	/**
	 * 领单
	 * @param businessKey 业务主键
	 * @param flowName 流程名称
	 * @param userId 领单人
	 */
	void claimTask(String businessKey, String flowName, String userId);

	/**
	 * 启动或完成任务
	 * @param businessKey 业务主键
	 * @param flowName 流程名称
	 * @param action 执行动作
	 * @throws Exception
	 */
	void completeTask(String businessKey, String flowName, String action);

	/**
	 * 获取下一状态
	 * @param businessKey 业务主键
	 * @param flowName 流程名称
	 * @param action 执行动作
	 * @throws Exception
	 */
	String getNextRoute(String businessKey, String flowName, String action);

}
