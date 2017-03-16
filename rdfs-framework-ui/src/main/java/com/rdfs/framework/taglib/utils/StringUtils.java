package com.rdfs.framework.taglib.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class StringUtils {

	public static boolean isBlank(String str){
		if(str == null || str.equals("")){
			return true;
		}
		return false;
	}

	public static boolean isBlankObj(Object obj) {
		if (null == obj) {
			return true;
		}
		if (obj instanceof String) {
			if (isBlank(obj.toString()) || "null".equals(obj.toString()))
				return true;
		}
		if (obj instanceof List) {
			List<?> ls = (List<?>) obj;
			if (ls.isEmpty())
				return true;
		}
		if (obj instanceof Map) {
			Map<?,?> m = (Map<?,?>) obj;
			if (m.isEmpty())
				return true;
		}
		if (obj instanceof Set) {
			Set<?> s = (Set<?>) obj;
			if (s.isEmpty())
				return true;
		}
		if (obj instanceof Object[]) {
			Object[] array = (Object[]) obj;
			if (array.length == 0)
				return true;
		}

		return false;
	}
}
