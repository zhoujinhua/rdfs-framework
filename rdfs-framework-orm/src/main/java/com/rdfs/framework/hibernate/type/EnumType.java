package com.rdfs.framework.hibernate.type;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import com.rdfs.framework.hibernate.utils.EnumUtils;

@SuppressWarnings("rawtypes")
public class EnumType implements UserType,ParameterizedType{

	private static final Map<String,Integer> SQLTYPE_MAP = Collections.synchronizedMap(new HashMap<String,Integer>());
	private Class<java.lang.Enum> enumClass;

	private int[] sqlTypes;
	private java.lang.Enum[] enums = null;
	private Method codeMethod = null;
	
	static{
		SQLTYPE_MAP.put("VARCHAR", Types.VARCHAR);
		SQLTYPE_MAP.put("CHAR", Types.CHAR);
		SQLTYPE_MAP.put("INTEGER", Types.INTEGER);
		SQLTYPE_MAP.put("INT", Types.INTEGER);
		SQLTYPE_MAP.put("NUMERIC", Types.NUMERIC);
		SQLTYPE_MAP.put("BIGINT", Types.BIGINT);
		SQLTYPE_MAP.put("SMALLINT", Types.SMALLINT);
		SQLTYPE_MAP.put("TINYINT", Types.TINYINT);
		SQLTYPE_MAP.put("DECIMAL", Types.DECIMAL);
		SQLTYPE_MAP.put("TIMESTAMP", Types.TIMESTAMP);
		SQLTYPE_MAP.put("DATE", Types.DATE);
		SQLTYPE_MAP.put("DOUBLE", Types.DOUBLE);
		SQLTYPE_MAP.put("FLOAT", Types.FLOAT);
		SQLTYPE_MAP.put("BLOB", Types.BLOB);
		SQLTYPE_MAP.put("CLOB", Types.CLOB);
		SQLTYPE_MAP.put("NULL", Types.NULL);
	}
	
	@Override
	public Object assemble(Serializable arg0, Object arg1)
			throws HibernateException {
		return arg0;
	}

	@Override
	public Object deepCopy(Object arg0) throws HibernateException {
		return (java.lang.Enum)arg0;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		return (Serializable) arg0;
	}

	@Override
	public boolean equals(Object arg0, Object arg1) throws HibernateException {
		return arg0 == arg1;
	}

	@Override
	public int hashCode(Object arg0) throws HibernateException {
		return arg0.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names,
			SessionImplementor arg2, Object arg3) throws HibernateException,
			SQLException {
		PersistentEnum metaInfo = enumClass.getAnnotation(PersistentEnum.class);
		if(metaInfo != null){
			if(metaInfo.readonly()){
				String codeField = metaInfo.itemCode();
				if(codeField==null || codeField.equals("")){
					java.lang.Enum.valueOf(enumClass, rs.getString(names[0]));
				}else{
					try{
						if(enums==null){
							Method valuesMethod = enumClass.getDeclaredMethod("values", new Class[]{});
							enums =  (java.lang.Enum[]) valuesMethod.invoke(null,null);
						}
						if(codeMethod == null){
							codeMethod = enumClass.getMethod("get"+codeField.substring(0,1).toUpperCase()+codeField.substring(1), null);
						}
						Object val = rs.getObject(names[0]);
						if(val!=null){
							for(java.lang.Enum e : enums){
								if(val.equals(codeMethod.invoke(e, null))){
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
			Class [] ifaces = enumClass.getInterfaces();
			if(ifaces==null || ifaces.length == 0){
				java.lang.Enum.valueOf(enumClass, rs.getString(names[0]));
			}
			for(Class iface : ifaces){
				if(iface.equals(Enum.class)){
					return EnumUtils.valueOfIgnoreType(enumClass, rs.getObject(names[0]));
				}
			}
		}
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		Object val = null;
		if(value!=null){
			if(value instanceof Enum){
				val = ((Enum)value).getValue();
				st.setObject(index, val, sqlTypes[0]);
			}else if(value instanceof java.lang.Enum){
				Class<java.lang.Enum> clazz = (Class<java.lang.Enum>) value.getClass();
				PersistentEnum metaInfo = clazz.getAnnotation(PersistentEnum.class);
				if(metaInfo != null){
					String code = metaInfo.itemCode();
					String codeValue = null;
					if(code==null || code.equals("")){
						codeValue = ((java.lang.Enum)value).name();
					}else{
						try {
							codeValue = String.valueOf(PropertyUtils.getProperty(value,code));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
					
					st.setObject(index, codeValue, sqlTypes[0]);
				}else{
					st.setNull(index, sqlTypes[0]);
				}
				
			}else{
				st.setNull(index, sqlTypes[0]);
			}
			
		}else{
			st.setNull(index, sqlTypes[0]);
		}
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2)
			throws HibernateException {
		return arg0;
	}

	@Override
	public Class returnedClass() {
		return enumClass;
	}

	@Override
	public int[] sqlTypes() {
		  return sqlTypes;
	}

	@Override
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enumClass");
		String sqlType = parameters.getProperty("sqlType");
		Integer type = SQLTYPE_MAP.get(sqlType!=null?sqlType.toUpperCase():null);
		
		sqlTypes = new int[]{type==null?Types.VARCHAR:type};
		try {
			enumClass = (Class<java.lang.Enum>) Class.forName(enumClassName);
		} catch (ClassNotFoundException exception) {
			throw new HibernateException("enum class not found", exception);
		}		
	}

}
