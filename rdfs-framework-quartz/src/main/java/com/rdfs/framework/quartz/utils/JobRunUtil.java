package com.rdfs.framework.quartz.utils;

import java.lang.reflect.Method;
import java.util.Date;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdfs.framework.core.spring.SpringContextBeanFactory;
import com.rdfs.framework.quartz.Constants.Constants;
import com.rdfs.framework.quartz.entity.QzJobInfo;
import com.rdfs.framework.quartz.entity.QzJobLog;
import com.rdfs.framework.quartz.service.JobInfoService;
import com.rdfs.framework.taglib.utils.StringUtils;

public class JobRunUtil {
	
	public static JobInfoService jobInfoService = SpringContextBeanFactory.getBean("jobInfoServiceImpl");
	private static Logger logger = LoggerFactory.getLogger(JobRunUtil.class);
	
	public static void run(QzJobInfo info){
		if(!StringUtils.isBlankObj(info)){
			logger.info("准备运行定时调度任务:" + info.toString());
			if(StringUtils.isBlank(info.getIp()) || (!StringUtils.isBlank(info.getIp()) && 
					(info.getIp().equals(IpUtil.getHostName()) || info.getIp().equals(IpUtil.getIp())))){
				QzJobLog jobLog = new QzJobLog(info, Constants.IS.NO, new Date());
				try{
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
							}
						}
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					jobInfoService.saveEntity(jobLog);
					logger.info("结束运行定时调度任务:" + info.toString());
				}
			}
		}
	}
}
