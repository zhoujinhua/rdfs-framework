package com.rdfs.framework.hibernate.utils;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdfs.framework.hibernate.type.PersistentEnum;

/**
 * 枚举工具包
 */
public final class EnumUtils {
	
	private static final Logger log = LoggerFactory.getLogger(EnumUtils.class);
	
	/**
	 * 根据值查找枚举对象
	 * @param enumType
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends java.lang.Enum<T>> T valueOf(final Class <T> enumType,final Object value,final boolean ignoreType){
		PersistentEnum metaInfo = enumType.getAnnotation(PersistentEnum.class);
		if(metaInfo != null){
			if(metaInfo.readonly()){
				String codeField = metaInfo.itemCode();
				if(codeField==null || codeField.equals("")){
					return Enum.valueOf(enumType, (String)value);
				}else{
					try{
						T[] enums = null;
						if(enums==null){
							Method valuesMethod = enumType.getDeclaredMethod("values", new Class[]{});
							enums = (T[]) valuesMethod.invoke(null,null);
						}
						Method codeMethod = enumType.getMethod("get"+codeField.substring(0,1).toUpperCase()+codeField.substring(1), null);
						if(value!=null){
							for(T e : enums){
								if(!ignoreType && value.equals(codeMethod.invoke(e, null))){
									return e;
								}else if(ignoreType && String.valueOf(value).equals(codeMethod.invoke(e, null))){
									return e;
								}
							}
						}
					}catch(Exception e){
						throw new RuntimeException(e);
					}
				}
			}
		}else{
			Class [] ifaces = enumType.getInterfaces();
			if(ifaces!=null && ifaces.length > 0){
				for(Class iface : ifaces){
					if(iface.equals(com.rdfs.framework.hibernate.type.Enum.class)){
						return valueForInface(enumType, value,ignoreType);
					}
				}
			}
			return Enum.valueOf(enumType, (String)value);
			
		}
		return null;
		
		
	}
	
	private static <T extends java.lang.Enum<T>> T valueForInface(final Class <T> enumType,final Object value,final boolean ignoreType){
		try {
			Method method = enumType.getMethod("values", new Class[]{});
			T[] enums = (T[]) method.invoke(enumType, new Object[0]);
			
			//迭代所有枚举值，当匹配到value时返回该枚举
			for(T e : enums){
				if(!ignoreType && ((com.rdfs.framework.hibernate.type.Enum)e).getValue().equals(value)){
					return e;
				}else if(ignoreType && String.valueOf(((com.rdfs.framework.hibernate.type.Enum)e).getValue()).equals(String.valueOf(value))){
					return e;
				}
			}
		} catch (Exception e) { 
			log.error("method values is not found in "+enumType.getClass().getName(), e);
			return null;
		}
		return null;
	}
	
	public static <T extends java.lang.Enum<T>> T valueOf(Class <T> enumType,Object value){
		return valueOf(enumType,value,false);
	}
	
	/**
	 * 忽略数据类型匹配对应的枚举
	 * @param enumType
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends java.lang.Enum<T>> T valueOfIgnoreType(Class <T> enumType,Object value){
		return valueOf(enumType,value,true);
	}
	
	/**
	 * 返回枚举的value值
	 * @param e
	 * @return
	 */
	public static Object getValue(Enum e){
		if(e==null){
			return null;
		}
		if(e instanceof com.rdfs.framework.hibernate.type.Enum){
			return ((com.rdfs.framework.hibernate.type.Enum) e).getValue();
		}
		Class<? extends Enum> enumType = e.getClass();
		PersistentEnum metaInfo = enumType.getAnnotation(PersistentEnum.class);
		if(metaInfo != null){
			String codeField = metaInfo.itemCode();
			if(codeField==null || codeField.equals("")){
				return e.name();
			}
			try {
				Method codeMethod = enumType.getMethod("get"+codeField.substring(0,1).toUpperCase()+codeField.substring(1), null);
				return codeMethod.invoke(e, null);
			} catch (Exception e1) {
				try {
					throw new Exception("枚举取值失败",e1);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return e.name();
	}
}
