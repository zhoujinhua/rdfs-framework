package com.rdfs.framework.core.utils;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdfs.framework.core.bean.HashDto;
import com.rdfs.framework.core.exeption.RdfsException;
import com.rdfs.framework.core.service.Dto;


/**
 * <b>读取属性配置文件</b>
 */
public class PropertyUtil {
	
	private static Logger log = LoggerFactory.getLogger(PropertyUtil.class);
	
	private static Dto parametersDto = null;

	private static Properties defaultProperties;
	
	public static final String DEFAULT = "default";
	
	//to add other properties
	//public static final String OTHER = "other";
	
	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = PropertyUtil.class.getClassLoader();
		}
		try {
			if (log.isDebugEnabled()) {
				log.debug("开始装载属性资源参数文件...");
			}
			parametersDto = new HashDto();
			//application.properties
			InputStream is = classLoader.getResourceAsStream("rdfs.properties");
			defaultProperties = new Properties();
			defaultProperties.load(is);
			parametersDto.put(DEFAULT, defaultProperties);
			//to add other properties
		} catch (Exception e) {
			throw new RdfsException("装载属性资源参数文件出错.", e);
		}
	}
	
	/**
	 * 返回缺省属性文件[application.properties]属性值
	 * 
	 * @param pKey
	 * @return String
	 */
	public static String getProperty(String pKey) {
		String value = defaultProperties.getProperty(pKey, "");
		return value;
	}
	
	/**
	 * 返回指定属性文件的属性值
	 * 
	 * @param pKey
	 * @return String
	 */
	public static String getProperty(String pKey, String pType) {
		Properties properties = (Properties)parametersDto.get(pType);
		String value = properties.getProperty(pKey, "");
		return value;
	}
}
