package com.rdfs.framework.core.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import com.rdfs.framework.core.utils.RdfsUtils;

public final class DateConverterFactory implements
		ConverterFactory<String, java.util.Date> {
	private SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat SDF=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public <T extends Date> Converter<String, T> getConverter(
			Class<T> targetType) {
		return new StringToDate(targetType);
	}

	private class StringToDate<T extends Date> implements Converter<String, T> {

		private final Class<T> dateType;

		public StringToDate(Class<T> dateType) {
			this.dateType = dateType;
		}

		public T convert(String source) {
			if (source.length() == 0) {
				return null;
			}
			if (RdfsUtils.isEmpty(source)) {
				return null;
			}
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return (T) df.parse(source.toString());
			} catch (ParseException e) {
				try {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					return (T) df.parse(source.toString());
				} catch (ParseException e1) {
					try {
						SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
						return (T) df.parse(source.toString());
					} catch (ParseException e2) {
						try {
							SimpleDateFormat dfParse = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
							return (T) dfParse.parse(source.toString());
						} catch (ParseException e3) {
							e3.printStackTrace();
						}
					}
				}
			}
			return null;
		}
	}

}
