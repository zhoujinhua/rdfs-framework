package com.rdfs.framework.quartz.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdfs.framework.core.spring.SpringContextBeanFactory;
import com.rdfs.framework.core.utils.RdfsUtils;
import com.rdfs.framework.mail.utils.MailSendUtil;
import com.rdfs.framework.quartz.Constants.Constants;
import com.rdfs.framework.quartz.entity.QzJobInfo;
import com.rdfs.framework.quartz.entity.QzJobLog;
import com.rdfs.framework.quartz.entity.QzJobRelation;
import com.rdfs.framework.quartz.service.JobInfoService;
import com.rdfs.framework.taglib.utils.StringUtils;

public class JobRunUtil {
	
	public static JobInfoService jobInfoService = SpringContextBeanFactory.getBean("jobInfoServiceImpl");
	private static Logger logger = LoggerFactory.getLogger(JobRunUtil.class);
	
	public static void run(QzJobInfo info){
		info = jobInfoService.getEntityById(QzJobInfo.class, info.getId(), true);
		if(!StringUtils.isBlankObj(info)){
			logger.info("准备运行定时调度任务:" + info.toString());
			if(StringUtils.isBlank(info.getIp()) || (!StringUtils.isBlank(info.getIp()) && 
					(info.getIp().equals(IpUtil.getHostName()) || info.getIp().equals(IpUtil.getIp())))){
				QzJobLog jobLog = new QzJobLog(info, Constants.IS.NO, new Date());
				try{
					run(jobLog, info);
				}catch(Exception e){
					logger.error("定时任务调度异常,", e);
					if(info.getSendMail()!=null && !Constants.MAIL.EXCEPTION.equals(info.getSendMail())){
						sendMail(info, jobLog, Constants.MAIL.EXCEPTION);
					}
				}finally{
					jobInfoService.saveEntity(jobLog);
					logger.info("结束运行定时调度任务:" + info.toString());
					
					if(jobLog.getIsSuccess().equals(Constants.IS.YES) && info.getRelations()!=null && !info.getRelations().isEmpty()){
						for(QzJobRelation jobRelation : info.getRelations()){
							run(jobRelation.getJobInfo());
						}
					}
					
					if(info.getSendMail()!=null && !Constants.MAIL.COMPLETE.equals(info.getSendMail())){
						sendMail(info, jobLog, Constants.MAIL.COMPLETE);
					}
				}
			}
		}
	}
	
	public static QzJobLog run(QzJobLog jobLog, QzJobInfo info){
		if(!StringUtils.isBlank(info.getType())){
			//jobInfoService.updateEntity(info, "");
			if(info.getType().equals(Constants.TYPE.HTTP)){
				try{
					String result = HttpUtils.invokeGet(null, info.getHttpUrl(), 30000000);
					jobLog.setIsSuccess(Constants.IS.YES);
					jobLog.setMessage(result);
				}catch(Exception e){
					jobLog.setMessage(e.getMessage());
					logger.info("运行定时调度任务:" + info.toString()+"失败,",e);
					throw new RuntimeException(e);
				}
			}
			if(info.getType().equals(Constants.TYPE.NATIVE)){
				Method method = null;
				try{
					Class<?> clazz = Class.forName(info.getClassName());
					Object bean = clazz.newInstance();
					if(bean instanceof Job){
						method = clazz.getMethod("execute");
					}
					method.invoke(bean);
					jobLog.setIsSuccess(Constants.IS.YES);
				}catch(Exception e){
					jobLog.setMessage(e.getMessage());
					logger.info("运行定时调度任务:" + info.toString()+"失败,",e);
					throw new RuntimeException(e);
				}
			}
			if(info.getType().equals(Constants.TYPE.SHELL)){
				try{
					boolean flag = new ShellUtil(info.getHost(), info.getPort(), info.getUserName(), info.getPassword()).executeCommands(info.getCmd().split(";"));
					if(flag){
						jobLog.setIsSuccess(Constants.IS.YES);
					}
				}catch(Exception e){
					jobLog.setMessage(e.getMessage());
					logger.info("运行定时调度任务:" + info.toString()+"失败,",e);
					throw new RuntimeException(e);
				}
			}
		}
		return jobLog;
	}
	
	public static void sendMail(QzJobInfo jobInfo, QzJobLog jobLog, String type){
		String subject = "";
		if(type.equals(Constants.MAIL.COMPLETE)){
			subject = "【执行完成】 " + RdfsUtils.date2String(new Date(), "YYYY-MM-DD HH:MI:SS") +" " + jobInfo.getName();
		}
		if(type.equals(Constants.MAIL.EXCEPTION)){
			subject = "【执行异常】 " + RdfsUtils.date2String(new Date(), "YYYY-MM-DD HH:MI:SS") +" " + jobInfo.getName();
		}
		List<String> mailList = new ArrayList<>();
		for(String mail : jobInfo.getEmail().split(";")){
			mailList.add(mail);
		}
		new MailSendUtil(subject, jobLog.getMessage(), mailList).send();
	}
}
