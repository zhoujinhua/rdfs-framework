package com.rdfs.framework.quartz.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.SchedulerException;

import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.core.spring.SpringContextBeanFactory;
import com.rdfs.framework.quartz.entity.QzJobInfo;
import com.rdfs.framework.quartz.service.JobInfoService;
import com.rdfs.framework.quartz.utils.QuartzUtil;

@WebListener
public class QuartzInitListener implements ServletContextListener{
	
	public static JobInfoService jobInfoService;
	
	public void initTask() {

		// 可执行的任务列表
		List<QzJobInfo> list = jobInfoService.getJobInfos(Constants.IS.YES);

		for (QzJobInfo jobInfo : list) {
			try {
				QuartzUtil.mergeSchedule(jobInfo);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		jobInfoService = SpringContextBeanFactory.getBean("jobInfoServiceImpl");
		initTask();
	}

}