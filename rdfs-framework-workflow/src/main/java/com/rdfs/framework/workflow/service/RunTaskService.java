package com.rdfs.framework.workflow.service;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwRunTask;

public interface RunTaskService extends HibernateService{

	CwRunTask getRunTask(String businessKey, String flowName);

	CwRunTask getRunTask(String businessKey, Integer flowId);

}
