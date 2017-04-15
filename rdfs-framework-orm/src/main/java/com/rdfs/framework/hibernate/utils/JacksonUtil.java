package com.rdfs.framework.hibernate.utils;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rdfs.framework.hibernate.serializer.EnumDeserializer;
import com.rdfs.framework.hibernate.serializer.EnumSerializer;

public final class JacksonUtil {
	
	public static ObjectMapper objectMapper = new ObjectMapper();
	private static Logger logger = LoggerFactory.getLogger(JacksonUtil.class);
	
	static {
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		SimpleModule module = new SimpleModule();
		module.addSerializer(com.rdfs.framework.hibernate.type.Enum.class, new EnumSerializer());
		module.addDeserializer(com.rdfs.framework.hibernate.type.Enum.class, new EnumDeserializer());
		objectMapper.registerModule(module);
	}
	
	public static <T> T fromJson(String jsonStr, Class<T> valueType) {
		try {
			return objectMapper.readValue(jsonStr, valueType);
		} catch (Exception e) {
			logger.error("Java 转 JSON 出错！", e);
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromJson(String jsonStr, TypeReference<T> valueTypeRef) {
		try {
			return objectMapper.readValue(jsonStr, valueTypeRef);
		} catch (Exception e) {
			logger.error("JSON 转 Java 出错！", e);
			throw new RuntimeException(e);
		}
	}

	public static String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error("JSON 转 Java 出错！", e);
			throw new RuntimeException(e);
		}
	}
}