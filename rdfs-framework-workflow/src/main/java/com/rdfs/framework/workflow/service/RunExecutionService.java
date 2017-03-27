package com.rdfs.framework.workflow.service;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwRunExecution;

public interface RunExecutionService extends HibernateService {

	/**
	 * 根据业务主键+流程代码+节点代码查找最近一次的提交记录
	 * @param businessKey 业务主键
	 * @param flowName 流程代码
	 * @param userIdentify 节点代码
	 * @return
	 */
	CwRunExecution getRunExecution(String businessKey, String flowName, String userIdentify);

	/**
	 * 根据业务主键+流程ID+节点代码查找最近一次的提交记录
	 * @param businessKey 业务主键
	 * @param flowId 流程ID
	 * @param userIdentify 节点代码
	 * @return
	 */
	CwRunExecution getRunExecution(String businessKey, Integer flowId, String userIdentify);

}
