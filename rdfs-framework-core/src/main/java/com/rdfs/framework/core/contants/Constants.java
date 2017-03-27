package com.rdfs.framework.core.contants;

public class Constants {
    public static final int DEFAULT_PAGE_SIZE = 10;
    
    public static final String COMMAND = "command";
    
    /**
	 * Dto对象中的内部变量：交易状态码
	 */
	public static final String APPCODE_KEY = "appcode";
	
	/**
	 * Dto对象中的内部变量：交易状态信息
	 */
	public static final String APPMSG_KEY = "msg";
	
	/**
	 * 日期格式
	 */
	public static final String DATA = "yyyy-MM-dd";
	
	/**
	 * 日期时间格式
	 */
	public static final String DATATIME = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 排序器在参数对象中的Key
	 */
	public static final String ORDER_KEY = "order";
	
	/**
	 * 控制台醒目标记1
	 */
	public static final String CONSOLE_FLAG1 = "● ";
	
	/**
	 * 控制台醒目标记2
	 */
	public static final String CONSOLE_FLAG2 = "●● ";

	/**
	 * 控制台醒目标记3
	 */
	public static final String CONSOLE_FLAG3 = "●●● ";
    
	/**
	 * App应用全局Key标识
	 */
	public static final String APP_KEY = "xcas";

	/**
	 * 是否标识
	 */
	public static final class IS {
		public static final String YES = "1";
		public static final String NO = "0";
	}
	
	/**
	 * 用户标识
	 */
	public static final class USER_TYPE {
		public static final String CS = "01";
		public static final String SD = "02";
	}
	
	/**
	 * 查询标识
	 */
	public static final class OPER {
		public static final String EQ = "eq";
		public static final String LIKE = "like";
	}
	
	/**
	 * 排序标识
	 */
	public static final class ORDER {
		public static final String DESC = "desc";
		public static final String ASC = "asc";
	}
	
	/**
	 * Redis全局Key前缀
	 */
	public static final class KEYS {
		// 全局KEY
		public static final String GLOBAL = APP_KEY + ":";
		// 应用上下文
		public static final String CXT = GLOBAL + "cxt_";
		// 序列号
		public static final String ID = GLOBAL + "id_";
		// 功能权限授权命名空间 (方便缓存重置)
		public static final String FUNCTION_GRANT = GLOBAL + "grant_";
		// 全局参数
		public static final String PARAM_KEY = GLOBAL + "param_";
		// 数据字典
		public static final String DIC_KEY = GLOBAL + "dic_";
		
		public static final String REGION_KEY = GLOBAL + "region_";
		//省份
		public static final String REGION_PROVINCE_KEY = GLOBAL + "region_province_";

		public static final String REGION_CITY_KEY = GLOBAL + "region_city_";
		
		public static final String DEALER_KEY = GLOBAL + "dealer_";
		
		public static final String DEALER_COMPANY_KEY = GLOBAL + "dealer_company_";
		
		public static final String USER_KEY = GLOBAL + "user_";
		//菜单项
		
		public static final String APP_SEQ = GLOBAL + "app_seq_";
		
		public static final String BRAND_KEY = GLOBAL + "brand_";

		public static final String BRAND_SERIES_KEY = GLOBAL + "brand_series_";
	}
	
	public static final class REDIS_TYPE{
		public static final String STRING = "resource_";
		public static final String LIST = "resource_";
		public static final String MAP = "resource_";
		public static final String SET = "resource_";
	}
}
