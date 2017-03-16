package com.rdfs.framework.hibernate.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * get and set field
 *
 */
public class BeanInvokeUtils {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object invokeMethodMain(Object obj , String methodName) throws Exception{
		Class ownerClass= obj.getClass();
		methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
		
		Method method = null;
		
		try{
			method = ownerClass.getMethod("get"+methodName);
		} catch(SecurityException e){} 
		catch (NoSuchMethodException e){
			return null;
		}
		return method.invoke(obj);
	}
	
	public static Object invokeMethod(Object obj , String methodName) throws Exception{
		if(methodName.indexOf(".")!=-1){
			String[] part = methodName.split("\\.");
			int i = 0;
			while(i < part.length){
				String method = part[i].substring(0, 1).toUpperCase() + part[i].substring(1);
				obj = invokeMethodMain(obj, method);
				if(StringUtils.isEmpty(obj)){
					break;
				}
				i++;
			}
		} else {
			obj = invokeMethodMain(obj, methodName);
		}
		return obj;
		
	}
	
	public static Object cloneMethod(Object sour, Object dest, String...params){
		if(params != null && params.length != 0){
			for(String param : params){
				try {
					Field field = dest.getClass().getDeclaredField(param);
					field.setAccessible(true);
					
					field.set(dest, invokeMethod(sour, param));
				} catch(Exception e){
					System.out.println("do nothing...");
				}
			}
		}
		return dest;
	}
	public static Object cloneMethodAll(Object sour, Object dest, String...params){
		List<String> fieldNames = new ArrayList<>();
		if(params!=null && params.length!=0){
			for(String param : params){
				fieldNames.add(param);
			}
		}
		for(Field field : dest.getClass().getDeclaredFields()){
			try {
				String fieldName = field.getName();
				if(!fieldNames.contains(fieldName)){
					Object value = invokeMethodMain(sour, fieldName);
					field.setAccessible(true);
					
					field.set(dest, value);
				}
			} catch(Exception e){
				System.out.println("do nothing...");
			}
		}
		return dest;
	}
	
	/**
	 * 获取静态变量
	 */
	public static Object invokeStaticField(Class<?> clazz, String methodName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Field field = clazz.getDeclaredField(methodName);
		return field.get(null);
	}
}
