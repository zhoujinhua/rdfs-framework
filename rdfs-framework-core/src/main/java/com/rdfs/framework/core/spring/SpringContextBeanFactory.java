package com.rdfs.framework.core.spring;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.rdfs.framework.core.exeption.CustomerException;

public class SpringContextBeanFactory {

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		T springBean = null;
		try {
			springBean = (T) SpringApplicationContextHolder
					.getWebApplicationContext().getBean(name);
		} catch (Exception e) {
			try{
				springBean = (T) SpringDispatcherContextHolder.getWebApplicationContext()
				.getBean(name);
			} catch(Exception ex){
				String msg = "Spring容器中没有找到ID名称为:[" + name + "]的SpringBean组件,请检查!";
				throw new CustomerException(msg, ex);
			}
		}
		return springBean;
	}

	public static <T> T getBean(Class<T> clazz) {
		T springBean = null;
		try {
			springBean = SpringApplicationContextHolder.getWebApplicationContext()
					.getBean(clazz);
		} catch (Exception e) {
			try{
				
				springBean = SpringDispatcherContextHolder.getWebApplicationContext()
						.getBean(clazz);
			} catch(Exception ex){
				String msg = "Spring容器中没有找到Class名称为:[" + clazz.getSimpleName() + "]的SpringBean组件,请检查!";
				throw new CustomerException(msg, ex);
			}
		}
		return springBean;
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		try {
			return getBean(name);
		} catch (Exception e) {
			try {
				return getBean(clazz);
			} catch (Exception ex) {
				return null;
			}
		}
	}

	public static Map<String, BeanDefinition> getApplicationBeanDefinitions() {
		Map<String, BeanDefinition> map = new HashMap<String, BeanDefinition>();
		XmlWebApplicationContext context = (XmlWebApplicationContext) SpringApplicationContextHolder
				.getWebApplicationContext();
		ConfigurableListableBeanFactory factory = context.getBeanFactory();
		String[] names = factory.getBeanDefinitionNames();
		for (String name : names) {
			map.put(name, factory.getBeanDefinition(name));
		}
		return map;
	}

	public static Map<String, BeanDefinition> getDispatcherBeanDefinitions() {
		Map<String, BeanDefinition> map = new HashMap<String, BeanDefinition>();
		XmlWebApplicationContext context = (XmlWebApplicationContext) SpringDispatcherContextHolder
				.getWebApplicationContext();
		ConfigurableListableBeanFactory factory = context.getBeanFactory();
		String[] names = factory.getBeanDefinitionNames();
		for (String name : names) {
			map.put(name, factory.getBeanDefinition(name));
		}
		return map;
	}

}
