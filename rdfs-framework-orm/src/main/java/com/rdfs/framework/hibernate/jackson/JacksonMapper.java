package com.rdfs.framework.hibernate.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rdfs.framework.hibernate.serializer.EnumDeserializer;
import com.rdfs.framework.hibernate.serializer.EnumSerializer;

public class JacksonMapper extends ObjectMapper{

	/**
	 * jackson针对自定义枚举类型的序列化和反序列化工具
	 */
	private static final long serialVersionUID = 3233728166529273623L;

	public JacksonMapper(){
		super();
		SimpleModule module = new SimpleModule();
		module.addSerializer(com.rdfs.framework.hibernate.type.Enum.class, new EnumSerializer());
		module.addDeserializer(com.rdfs.framework.hibernate.type.Enum.class, new EnumDeserializer());
		registerModule(module);
	}
}
