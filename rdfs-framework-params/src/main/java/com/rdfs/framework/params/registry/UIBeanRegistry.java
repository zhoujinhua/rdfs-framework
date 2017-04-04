package com.rdfs.framework.params.registry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rdfs.framework.cache.service.impl.CacheParamsServiceImpl;
import com.rdfs.framework.core.spring.SpringContextBeanFactory;

public class UIBeanRegistry implements BeanDefinitionRegistryPostProcessor {
	
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
    	BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(CacheParamsServiceImpl.class);
    	beanDefinitionRegistry.registerBeanDefinition("cacheParamsServiceImpl", definition.getBeanDefinition());
    	
        Class<?> clazz;
		try {
			clazz = Class.forName("com.rdfs.framework.taglib.utils.CacheCxtUtil");
			BeanDefinitionBuilder uidefinition = BeanDefinitionBuilder.genericBeanDefinition(clazz);
			uidefinition.addPropertyValue("cacheDataService", SpringContextBeanFactory.getBean("cacheParamsServiceImpl"));
			beanDefinitionRegistry.registerBeanDefinition("cacheCxtUtil", uidefinition.getBeanDefinition());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		
	}
}