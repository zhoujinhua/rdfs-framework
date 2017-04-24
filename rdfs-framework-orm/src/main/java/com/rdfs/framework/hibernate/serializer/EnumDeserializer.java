package com.rdfs.framework.hibernate.serializer;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.rdfs.framework.hibernate.type.Enum;
import com.rdfs.framework.hibernate.utils.EnumUtils;

/**
 * 自定义枚举反序列化
 * @author zhoufei
 *
 */
public class EnumDeserializer extends JsonDeserializer<Enum>{

	@Override
	public Enum deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
        String value =  node.get("value").asText();
        if(!StringUtils.isBlank(value)){
        	String fieldName = jp.getCurrentName();
        	Object obj = jp.getCurrentValue();
        	try {
				Field field = obj.getClass().getDeclaredField(fieldName);
				return EnumUtils.valueOfNT(field.getType(), value);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
		return null;
	}

}
