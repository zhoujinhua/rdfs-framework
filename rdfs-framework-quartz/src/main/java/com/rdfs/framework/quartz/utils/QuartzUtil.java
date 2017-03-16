package com.rdfs.framework.quartz.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.rdfs.framework.core.spring.SpringContextBeanFactory;
import com.rdfs.framework.quartz.entity.QzJobInfo;
import com.rdfs.framework.quartz.factory.QuartzTaskFactory;

/**
 * Quartz2 帮助类
 * @author zhoufei
 *
 */
public class QuartzUtil {

	public static Scheduler scheduler = SpringContextBeanFactory.getBean("schedulerFactoryBean");
	
	/**
	 * 暂停
	 * @param reportInfo
	 * @throws SchedulerException
	 */
	public static void stopSchedule(QzJobInfo job) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey("task_" + job.getId(), "group_" + job.getId());
		scheduler.pauseJob(jobKey);
	}
	
	/**
	 * 恢复
	 * @param reportInfo
	 * @throws SchedulerException
	 */
	public static void startSchedule(QzJobInfo job) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey("task_" + job.getId(), "group_" + job.getId());
		scheduler.resumeJob(jobKey);
	}
	
	/**
	 * 删除
	 * @param reportInfo
	 * @throws SchedulerException
	 */
	public static void deleteSchedule(QzJobInfo job) throws SchedulerException{
		//Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey("task_" + job.getId(), "group_" + job.getId());
		if(jobKey != null){
			scheduler.deleteJob(jobKey);
		}
	}
	
	/**
	 * 立即运行
	 * @param reportInfo
	 * @throws SchedulerException
	 */
	public static void runSchedule(QzJobInfo job) throws SchedulerException{
		JobKey jobKey = JobKey.jobKey("task_" + job.getId(), "group_" + job.getId());
		scheduler.triggerJob(jobKey);
	}
	/**
	 * 更新
	 * @param reportInfo
	 * @throws SchedulerException
	 */
	public static void updateSchedule(QzJobInfo job) throws SchedulerException{
		TriggerKey triggerKey = TriggerKey.triggerKey("task_" + job.getId(), "group_" + job.getId());
		 
		//获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		//表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
		//按新的cronExpression表达式重新构建trigger
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
		.withSchedule(scheduleBuilder).build();
		 
		//按新的trigger重新设置job执行
		scheduler.rescheduleJob(triggerKey, trigger);
	}
	
	/**
	 * 无则添加，有则更新
	 * @param reportInfo
	 * @throws SchedulerException
	 */
	public static void mergeSchedule(QzJobInfo job) throws SchedulerException{
		TriggerKey triggerKey = TriggerKey.triggerKey("task_" + job.getId(), "group_" + job.getId());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 不存在，创建一个
		if (null == trigger) {
			JobDetail jobDetail = JobBuilder.newJob(QuartzTaskFactory.class)
					.withIdentity("task_" + job.getId(), "group_" + job.getId()).build();
			jobDetail.getJobDataMap().put("job", job);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
			// 按新的表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger()
					.withIdentity("task_" + job.getId(), "group_" + job.getId())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} else {
			// trigger已存在，则更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCron());
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}
}
