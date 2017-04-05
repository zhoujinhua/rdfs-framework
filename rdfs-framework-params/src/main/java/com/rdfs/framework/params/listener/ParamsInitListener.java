package com.rdfs.framework.params.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.rdfs.framework.cache.service.CacheParamsService;
import com.rdfs.framework.core.spring.SpringContextBeanFactory;
import com.rdfs.framework.taglib.utils.CacheCxtUtil;

@WebListener
public class ParamsInitListener implements ServletContextListener{
	
	public static CacheParamsService cacheParamsService;
	
	public void initParams() {
		cacheParamsService.cacheRegionData();
		cacheParamsService.cacheDictData();
		cacheParamsService.cacheParamData();
	}
	
	@SuppressWarnings({ "resource", "static-access" })
	public void registry(ServletContextEvent arg0){
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getServletContext());  
        ConfigurableApplicationContext context = (ConfigurableApplicationContext) ac;   
        //Bean的实例工厂  
        DefaultListableBeanFactory dbf = (DefaultListableBeanFactory) context.getBeanFactory();  
		Class<?> clazz;
		try {
			clazz = Class.forName("com.rdfs.framework.taglib.utils.CacheCxtUtil");
			BeanDefinitionBuilder uidefinition = BeanDefinitionBuilder.genericBeanDefinition(clazz);
			uidefinition.addPropertyValue("cacheDataService", SpringContextBeanFactory.getBean("cacheParamsServiceImpl"));
			dbf.registerBeanDefinition("cacheCxtUtil", uidefinition.getBeanDefinition());
			System.out.println(((CacheCxtUtil)SpringContextBeanFactory.getBean("cacheCxtUtil")).cacheDataService);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
		registry(arg0);
	}

}