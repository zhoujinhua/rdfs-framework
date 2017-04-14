package com.rdfs.framework.hibernate.serializer;

import java.io.IOException;
import java.lang.reflect.Field;

import org.springframework.stereotype.Component;
import com.rdfs.framework.hibernate.type.Enum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.rdfs.framework.core.utils.BeanInvokeUtils;

/**
 * 自定义枚举序列化
 */
@Component
public class EnumSerializer extends JsonSerializer<Enum> {  
    @Override  
    public void serialize(Enum ienum, JsonGenerator jgen, SerializerProvider provider)   
      throws IOException, JsonProcessingException {
    	jgen.writeStartObject();
    	Field[] fields = ienum.getClass().getDeclaredFields();
		for(Field field : fields){
			if(field.getType().getSimpleName().equals("String")){
				try {
					jgen.writeStringField(field.getName(), String.valueOf(BeanInvokeUtils.invokeMethod(ienum, field.getName())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		jgen.writeEndObject();
    }  
}  