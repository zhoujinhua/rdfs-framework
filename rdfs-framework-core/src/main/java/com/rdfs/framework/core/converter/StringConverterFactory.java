package com.rdfs.framework.core.converter;

import java.lang.reflect.Method;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.rdfs.framework.core.utils.StringUtils;

public class StringConverterFactory implements ConverterFactory<String, String>{

	@Override
	public <T extends String> Converter<String, T> getConverter(Class<T> targetType) {
		return new StringToString<T>(targetType);
	}
	private class StringToString<T extends String> implements Converter<String, T> {

		private final Class<T> stringType;

		public StringToString(Class<T> stringType) {
			this.stringType = stringType;
		}

		public T convert(String source) {
			if (source.length() == 0) {
				return null;
			}
			Object obj = StringUtils.KillEmpty(source);
			if(obj == null){
				return null;
			}
			return (T) StringUtils.KillEmpty(source);
		}
	}
}
