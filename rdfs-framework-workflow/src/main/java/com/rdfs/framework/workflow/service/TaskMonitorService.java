package com.rdfs.framework.workflow.service;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwRunTask;

public interface TaskMonitorService extends HibernateService {

	void invoke(CwRunTask runTask, CwNodeEvent nodeEvent, String eventName) throws Exception;

	void saveTaskMonitor(CwNodeEvent nodeEvent);

}
