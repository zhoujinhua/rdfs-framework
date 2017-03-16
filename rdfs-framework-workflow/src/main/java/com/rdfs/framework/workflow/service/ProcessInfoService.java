package com.rdfs.framework.workflow.service;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwProcessInfo;

public interface ProcessInfoService extends HibernateService{

	CwProcessInfo getLastProcess(String flowName);

}
