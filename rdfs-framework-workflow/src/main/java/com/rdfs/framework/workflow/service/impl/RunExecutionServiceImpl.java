package com.rdfs.framework.workflow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.entity.CwRunExecution;
import com.rdfs.framework.workflow.service.RunExecutionService;

@Service
public class RunExecutionServiceImpl extends HibernateServiceSupport implements RunExecutionService {

	@Override
	public CwRunExecution getRunExecution(String businessKey, String flowName, String userIdentify) {
		String hql = "from CwRunExecution where businessKey = '" + businessKey + "' and processInfo.code = '"
				+ flowName + "' and nodeEvent.currNode.code = '" + userIdentify + "' order by createTime desc";
		List<CwRunExecution> runExecutions = getList(hql);
		if(runExecutions!=null && !runExecutions.isEmpty()){
			return runExecutions.get(0);
		}
		return null;
	}

}
