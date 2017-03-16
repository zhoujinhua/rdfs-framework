package com.rdfs.framework.quartz.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.quartz.entity.QzJobInfo;

public interface JobInfoService extends HibernateService{

	void saveJobInfo(QzJobInfo job) throws SchedulerException;

	List<QzJobInfo> getJobInfos(String status);

	void runJob(QzJobInfo job);

}
