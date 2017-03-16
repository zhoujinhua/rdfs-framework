package com.rdfs.framework.hibernate.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;

import com.rdfs.framework.hibernate.bean.Page;


public class PageUtil {

	public static Logger logger = LoggerFactory.getLogger(PageUtil.class);
	
	public static Page getPage(HttpServletRequest request){
		Page page = new Page();
		try {
			page.setStart(ServletRequestUtils.getIntParameter(request, "start", 0));
			Integer length = ServletRequestUtils.getIntParameter(request, "length",0);
			if(length == null || length.intValue() == 0){
				length = 10;
			}
			page.setLimit(length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("封装page对象失败,",e);
		}
		return page;
	}
}
