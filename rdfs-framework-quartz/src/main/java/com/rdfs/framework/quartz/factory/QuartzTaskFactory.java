package com.rdfs.framework.quartz.factory;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdfs.framework.quartz.entity.QzJobInfo;
import com.rdfs.framework.quartz.utils.JobRunUtil;

public class QuartzTaskFactory implements Job {

	private Logger logger = LoggerFactory.getLogger(QuartzTaskFactory.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			System.out.println("任务运行...");
			QzJobInfo job = (QzJobInfo) context.getMergedJobDataMap().get("job");
			System.out.println("任务名称: [" + job.getName() + "]");
			JobRunUtil.run(job);
			System.out.println("任务运行完成...");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("任务执行失败,",e);
		}
	}
}