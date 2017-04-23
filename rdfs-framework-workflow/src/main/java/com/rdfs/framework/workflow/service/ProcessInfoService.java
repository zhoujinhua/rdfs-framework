package com.rdfs.framework.workflow.service;

import java.util.List;

import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwProcessInfo;


public interface ProcessInfoService extends HibernateService{

	CwProcessInfo getLastProcess(String flowName);

	List<CwProcessInfo> getProcessInfos();

	CwProcessInfo saveProcessInfo(CwProcessInfo processInfo);

	CwProcessInfo updateProcessVersion(CwProcessInfo processInfo);

	List<TreeDto> nodeTree(CwProcessInfo processInfo) throws Exception;

	CwProcessInfo saveCopyProcess(CwProcessInfo processInfo);

	CwProcessInfo getEntityById(CwProcessInfo processInfo);
	
	CwProcessInfo getEntityById(Class<CwProcessInfo> clazz, Integer id, boolean flag);

	CwProcessInfo updateProcessStatus(CwProcessInfo processInfo);

}
