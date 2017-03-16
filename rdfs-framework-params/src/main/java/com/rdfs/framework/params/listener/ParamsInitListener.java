package com.rdfs.framework.params.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.rdfs.framework.cache.service.CacheParamsService;
import com.rdfs.framework.core.spring.SpringContextBeanFactory;

@WebListener
public class ParamsInitListener implements ServletContextListener{
	
	public static CacheParamsService cacheParamsService;
	
	public void initParams() {
		cacheParamsService.cacheRegionData();
		cacheParamsService.cacheDictData();
		cacheParamsService.cacheParamData();
	}

	public ParamsInitListener(){
		System.out.println("ParamsInitListener初始化...");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("进入ParamsInitListener");
		cacheParamsService = SpringContextBeanFactory.getBean("cacheParamsServiceImpl");
		initParams();
	}

}