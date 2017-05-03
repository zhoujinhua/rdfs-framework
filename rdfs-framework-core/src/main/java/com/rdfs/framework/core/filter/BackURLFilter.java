package com.rdfs.framework.core.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rdfs.framework.core.bean.UserDto;
import com.rdfs.framework.core.utils.AuthUtil;

/**
 *拦截没有登陆情况下方view文件夹下的JSP页面
 * Version: 1.0
 */
@WebFilter(filterName="/urlFilter",urlPatterns="/view/*",initParams={@WebInitParam(name ="EXCLUDED_PAGES" , value = "index.jsp")}) 
public class BackURLFilter implements Filter{
	
	protected FilterConfig filterConfig = null;
	@Override
    public void init(FilterConfig filterConfig) throws ServletException {
    	this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String juid = AuthUtil.getJuid(request);
        UserDto userDto = AuthUtil.getUserDto(juid);
        
        boolean flag = AuthUtil.compareUserDto(request);
        if(!flag){
        	//ajax请求
        	if (request.getHeader("x-requested-with") != null&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
        		response.setStatus(911);
        	} else/* if(request.getRequestURI().indexOf("index.jsp")==-1)*/{
        		response.sendError(405);
        		return;
        	}
        } else {
        	AuthUtil.heartbeat(juid);
        	AuthUtil.setCurrentUserDto(userDto);
        }
        //System.out.println(new Date().toString() + "," + userDto.toString() + ",请求地址：" + request.getRequestURI() + ",请求状态："+response.getStatus());
		filterChain.doFilter(servletRequest, servletResponse);
    }
    
    @Override
    public void destroy() {
    	
    }
}
