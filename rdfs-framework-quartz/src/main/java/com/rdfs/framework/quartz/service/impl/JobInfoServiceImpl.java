package com.rdfs.framework.quartz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.core.service.TreeService;
import com.rdfs.framework.core.utils.StringUtils;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.quartz.entity.QzJobInfo;
import com.rdfs.framework.quartz.entity.QzJobRelation;
import com.rdfs.framework.quartz.service.JobInfoService;
import com.rdfs.framework.quartz.utils.JobRunUtil;
import com.rdfs.framework.quartz.utils.QuartzUtil;

@Service
public class JobInfoServiceImpl extends HibernateServiceSupport implements JobInfoService {

	@Autowired
	private TreeService treeService;
	
	@Override
	public void saveJobInfo(QzJobInfo job) throws SchedulerException {
		if(job.getId() == null){
			job.setCreateTime(new Date());
			saveEntity(job);
			if(job.getRelations()!=null && !job.getRelations().isEmpty()){
				for(QzJobRelation jobRelation : job.getRelations()){
					jobRelation.setJobInfo(job);
				}
			}
		} else {
			job.setCreateTime(new Date());
			updateEntity(job, "name", "group", "cron", "status", "type", "batch", "ip", "host", "port", "cmd", "httpUrl", "remark", "className", "createTime", "userName", "password");
			QzJobInfo jobInfo = getEntityById(QzJobInfo.class, job.getId(), true);
			jobInfo.getRelations().clear();
			
			if(job.getRelations()!=null && !job.getRelations().isEmpty()){
				for(QzJobRelation jobRelation : job.getRelations()){
					jobRelation.setJobInfo(job);
					jobInfo.getRelations().add(jobRelation);
				}
			}
		}
		if(job.getStatus().equals(Constants.IS.YES) && !StringUtils.isBlank(job.getCron())){
			QuartzUtil.mergeSchedule(job);
		} else {
			QuartzUtil.deleteSchedule(job);
		}
	}

	@Override
	public List<QzJobInfo> getJobInfos(String status){
		String hql = "from QzJobInfo where status = '" + status + "' and cron is not null and cron <> ''";
		return getList(hql);
	}

	@Override
	@Async
	@Transactional
	public void runJob(QzJobInfo job) {
		JobRunUtil.run(job);
	}

	@Override
	public List<TreeDto> jobTree(QzJobInfo job) throws Exception {
		List<QzJobInfo> jobInfoList = getList("from QzJobInfo where status = '" + Constants.IS.YES + "' and id <> " + job.getId());
		List<QzJobInfo> checkList = new ArrayList<>();
		if(job!=null && job.getId()!=null){
			job = getEntityById(QzJobInfo.class, job.getId(), true);
			if(job.getRelations()!=null && !job.getRelations().isEmpty()){
				for(QzJobRelation relation : job.getRelations()){
					checkList.add(relation.getJobInfo());
				}
			}
		}
		
		return treeService.getList(jobInfoList, false, "id", "name", null, checkList);
	}
}
