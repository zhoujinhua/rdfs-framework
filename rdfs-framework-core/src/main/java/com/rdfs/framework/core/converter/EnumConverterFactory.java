package com.rdfs.framework.core.converter;

import java.lang.reflect.Method;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.util.StringUtils;

public final class EnumConverterFactory implements
		ConverterFactory<String, Enum<?>> {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Enum<?>> Converter<String, T> getConverter(
			Class<T> targetType) {
		return new StringToEnum(targetType);
	}

	private class StringToEnum<T extends Enum<?>> implements Converter<String, T> {

		private final Class<T> enumType;

		public StringToEnum(Class<T> enumType) {
			this.enumType = enumType;
		}

		@SuppressWarnings("unchecked")
		public T convert(String source) {
			if (source.length() == 0) {
				return null;
			}
			Method method;
			try {
				method = enumType.getMethod("values", new Class[]{});
				T[] enums = (T[]) method.invoke(enumType, new Object[0]);
				
				for(T e : enums){
					Object obj = e.getClass().getMethod("getValue");
					if(!StringUtils.isEmpty(obj) && String.valueOf(obj).equals(source.trim())){
						return e;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

}
