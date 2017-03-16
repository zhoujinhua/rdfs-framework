package com.rdfs.framework.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * 字符串替换函数,String的replace函数不能处理'|'符号
	 * @param strSource   被替换的源字符串
	 * @param strFrom     要查找并替换的子字符串
	 * @param strTo     要替换为的子字符串
	 */
	public static String replace(String strSource, String strFrom, String strTo) {

		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;

		if (strFrom.equals("")) {
			return strSource;
		}
		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;
		return strDest;
	}

	/**
	 * 将普通字符串格式化成数据库认可的字符串格式
	 * @param input   要格式化的字符串
	 */
	public static String toSql(String input) {
		if (input!=null && !"".equals(input)) {
			return "";
		} else {
			return input.replaceAll("'", "''");
		}
	}

	/**
	 * 截取字符串左侧指定长度的字符串
	 * @param input  输入字符串
	 * @param count  截取长度
	 */
	public static String left(String input, int count) {
		if (input!=null && !"".equals(input)) {
			return "";
		}
		count = (count > input.length()) ? input.length() : count;
		return input.substring(0, count);
	}

	/**
	 * 截取字符串右侧指定长度的字符串
	 * @param input   输入字符串
	 * @param count   截取长度
	 */
	public static String right(String input, int count) {
		if (input!=null && !"".equals(input)) {
			return "";
		}
		count = (count > input.length()) ? input.length() : count;
		return input.substring(input.length() - count, input.length());
	}

	/**
	 * 从指定位置开始截取指定长度的字符串
	 * @param input  输入字符串
	 * @param index   截取位置，左侧第一个字符索引值是1
	 * @param count   截取长度
	 */
	public static String middle(String input, int index, int count) {
		if (input!=null && !"".equals(input)) {
			return "";
		}
		count = (count > input.length() - index + 1) ? input.length() - index
				+ 1 : count;
		return input.substring(index - 1, index + count - 1);
	}

	/**
	 * 分隔字符串成数组.
	 * 使用StringTokenizer，String的split函数不能处理'|'符号
	 * @param input   输入字符串
	 * @param delim   分隔符
	 */
	public static String[] splitString(String input, String delim) {
		if (input!=null && !"".equals(input)) {
			return new String[0];
		}
		ArrayList<String> al = new ArrayList<String>();
		for (StringTokenizer stringtokenizer = new StringTokenizer(input, delim); stringtokenizer
				.hasMoreTokens(); al.add(stringtokenizer.nextToken())) {
		}
		String result[] = new String[al.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = al.get(i);
		}
		return result;
	}

	/**
	 * 获得0-9的随机数
	 * @param length
	 * @return String
	 */
	public static String getRandomNumber(int length) {
		Random random = new Random();
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < length; i++) {
			buffer.append(random.nextInt(10));
		}
		return buffer.toString();
	}

	/**
	 * 获得0-9,a-z,A-Z范围的随机数
	 * @param length  随机数长度
	 */

	public static String getRandomChar(int length) {
		char[] chr = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z' };

		Random random = new Random();
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			buffer.append(chr[random.nextInt(62)]);
		}
		return buffer.toString();
	}

	/**
	 * 转换HTML中的特殊字符
	 * @param input
	 */
	public static String convertHTML(String input) {
		StringBuffer filtered = new StringBuffer();
		char c;
		for (int i = 0; i <= input.length() - 1; i++) {
			c = input.charAt(i);
			switch (c) {
			case '&':
				filtered.append("&amp;");
				break;
			case '<':
				filtered.append("&lt;");
				break;
			case '>':
				filtered.append("&gt;");
				break;
			case '"':
				filtered.append("&#034;");
				break;
			case '\'':
				filtered.append("&#039;");
				break;
			default:
				filtered.append(c);
			}
		}
		return (filtered.toString());
	}

	/**
	 * 在数字字符串前面补零
	 * @param sourceStr
	 * @param len
	 */
	static public String prefixZoreFill(String sourceStr, int len) {
		int prefix = len - sourceStr.length();
		if (prefix <= 0)
			return sourceStr;
		for (int i = 0; i < prefix; i++) {
			sourceStr = "0" + sourceStr;
		}
		return sourceStr;
	}

	/**
	 * 字符串全部替换
	 * @param str
	 * @param regex
	 * @param replacement
	 */
	static public String replaceAll(String str, String regex, String replacement) {
		if (str == null || str.compareTo("") == 0 || str.compareTo("null") == 0) {
			return str;
		}
		if (regex == null || regex.compareTo("null") == 0) {
			return str;
		}
		if (replacement == null || replacement.compareTo("null") == 0) {
			return str;
		}

		try {
			int iIndex, iFromIndex;
			String stmp;

			int iLen = regex.length();

			iFromIndex = 0;
			iIndex = str.toUpperCase().indexOf(regex.toUpperCase(), iFromIndex);
			stmp = "";
			while (iIndex >= 0) {
				stmp = stmp + str.substring(iFromIndex, iIndex) + replacement;
				str = str.substring(iIndex + iLen);
				iIndex = str.toUpperCase().indexOf(regex.toUpperCase(), iFromIndex);
			}
			stmp = stmp + str;

			return stmp;
		} catch (Exception e) {
			return str;
		}
	}

	public static String KillEmpty(String aStr) {
		if (aStr != null)
			aStr = aStr.trim();
		if (aStr == null || aStr.length() == 0)
			return null;
		return aStr;
	}

	public static boolean isBlank(String str) {
		if(str == null || "".equals(str)){
			return true;
		}
		return false;
	}
	
	public static String strFormatJson(String s) {        
        StringBuffer sb = new StringBuffer();        
        for (int i=0; i<s.length(); i++) {  
            char c = s.charAt(i);    
             switch (c){  
             case '\"':        
                 sb.append("\\\"");        
                 break;        
             case '\\':        
                 sb.append("\\\\");        
                 break;        
             case '/':        
                 sb.append("\\/");        
                 break;        
             case '\b':        
                 sb.append("\\b");        
                 break;        
             case '\f':        
                 sb.append("\\f");        
                 break;        
             case '\n':        
                 sb.append("\\n");        
                 break;        
             case '\r':        
                 sb.append("\\r");        
                 break;        
             case '\t':        
                 sb.append("\\t");        
                 break;        
             default:        
                 sb.append(c);     
             }  
         }      
        return sb.toString();     
    }
	
	public static String exprExcelWord(String str){
		return str.replaceAll("/", "").replaceAll("\\\\", "").replaceAll("\\?", "").replaceAll("\\*", "").replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\:", "");
	}
	
	public static String switListFormat(List<String> list){
		String result = " ";
		if(list!=null && !list.isEmpty()){
			for(String str : list){
				result += str + ",";
			}
		}
		return result.substring(0, result.length()-1);
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
	
	public static boolean compare(Object obj1, Object obj2){
		if((obj1==null&&obj2==null)||
    				(obj1!=null&&obj2!=null&&obj1.toString().equals(obj2.toString()))||
    				(obj1==null&&obj2!=null&&obj2.toString().equals(""))||
    				(obj2==null&&obj1!=null&&obj1.toString().equals(""))){
			return true;
		}
		return false;
	}
	
	/**
	* 正则表达式字符串
	* 要匹配的字符串
	* @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	*/
	public static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}