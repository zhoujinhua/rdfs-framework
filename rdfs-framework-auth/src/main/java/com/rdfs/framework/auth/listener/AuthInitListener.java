package com.rdfs.framework.auth.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.rdfs.framework.cache.service.CacheResourceService;
import com.rdfs.framework.core.spring.SpringContextBeanFactory;

@WebListener
public class AuthInitListener implements ServletContextListener {

	private CacheResourceService cacheResourceService;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		cacheResourceService = SpringContextBeanFactory.getBean("cacheResourceServiceImpl");
		cacheResourceService.cacheMenuData();
		cacheResourceService.cacheResourceData();
		cacheResourceService.cachePermsetData();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
