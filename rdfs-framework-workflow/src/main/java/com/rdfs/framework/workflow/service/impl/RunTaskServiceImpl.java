package com.rdfs.framework.workflow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.entity.CwRunTask;
import com.rdfs.framework.workflow.service.RunTaskService;


@Service
public class RunTaskServiceImpl extends HibernateServiceSupport implements RunTaskService {

	@Override
	public CwRunTask getRunTask(String businessKey, Integer flowId) {
		List<CwRunTask> runTasks = getList("from CwRunTask where businessKey = '" + businessKey + "' and processInfo.id = '" + flowId + "'"
				//+ " and status = '" + Constants.IS.YES + "'"
		);
		if(runTasks!=null && !runTasks.isEmpty()){
			return runTasks.get(0);
		}
		return null;
	}

}
