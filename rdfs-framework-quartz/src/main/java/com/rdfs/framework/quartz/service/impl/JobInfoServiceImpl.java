package com.rdfs.framework.quartz.service.impl;

import java.util.Date;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.quartz.entity.QzJobInfo;
import com.rdfs.framework.quartz.service.JobInfoService;
import com.rdfs.framework.quartz.utils.JobRunUtil;
import com.rdfs.framework.quartz.utils.QuartzUtil;

@Service
public class JobInfoServiceImpl extends HibernateServiceSupport implements JobInfoService {

	@Override
	public void saveJobInfo(QzJobInfo job) throws SchedulerException {
		if(job.getId() == null){
			job.setCreateTime(new Date());
			saveEntity(job);
		} else {
			job.setCreateTime(new Date());
			updateEntity(job);
		}
		if(job.getStatus().equals(Constants.IS.YES)){
			QuartzUtil.mergeSchedule(job);
		} else {
			QuartzUtil.deleteSchedule(job);
		}
	}

	@Override
	public List<QzJobInfo> getJobInfos(String status){
		String hql = "from QzJobInfo where status = '" + status + "'";
		return getList(hql);
	}

	@Override
	@Async
	public void runJob(QzJobInfo job) {
		JobRunUtil.run(job);
	}
}
