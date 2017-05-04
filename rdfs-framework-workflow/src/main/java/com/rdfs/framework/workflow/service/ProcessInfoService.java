package com.rdfs.framework.workflow.service;

import java.util.List;

import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.workflow.entity.CwProcessInfo;


public interface ProcessInfoService extends HibernateService{

	List<CwProcessInfo> getProcessInfos(CwProcessInfo processInfo);

	void saveProcessInfo(CwProcessInfo processInfo);

	void updateProcessVersion(CwProcessInfo processInfo);

	List<TreeDto> nodeTree(CwProcessInfo processInfo) throws Exception;

	void saveCopyProcess(CwProcessInfo processInfo);

	CwProcessInfo getEntityById(CwProcessInfo processInfo);
	/**
	 * 先取缓存，后取表
	 * @param id
	 * @return
	 */
	CwProcessInfo getEntityById(Integer id);
	
	void updateProcessStatus(CwProcessInfo processInfo);


}
