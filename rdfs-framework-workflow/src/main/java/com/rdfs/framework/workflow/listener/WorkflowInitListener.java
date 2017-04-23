package com.rdfs.framework.workflow.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.rdfs.framework.cache.service.CacheWorkflowService;
import com.rdfs.framework.core.spring.SpringContextBeanFactory;

@WebListener
public class WorkflowInitListener implements ServletContextListener{
	
	public static CacheWorkflowService cacheWorkflowService;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		cacheWorkflowService = SpringContextBeanFactory.getBean("cacheWorkflowServiceImpl");
		cacheWorkflowService.cacheWorkflowData();
	}

}