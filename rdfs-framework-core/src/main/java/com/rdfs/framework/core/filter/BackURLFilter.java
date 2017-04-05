package com.rdfs.framework.core.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rdfs.framework.core.bean.UserDto;
import com.rdfs.framework.core.utils.AuthUtil;

/**
 *拦截没有登陆情况下方view文件夹下的JSP页面
 * Version: 1.0
 */
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
        String uri = request.getServletPath()+ (request.getPathInfo() == null ? "" : request.getPathInfo());
        String juid = AuthUtil.getJuid(request);
        UserDto userDto = AuthUtil.getUserDto(juid);
        
        boolean flag = AuthUtil.compareUserDto(request);
        if(!flag && uri.indexOf("index.jsp")==-1){
        	response.sendError(405);
			return;
        } else {
        	AuthUtil.heartbeat(juid);
        	AuthUtil.setCurrentUserDto(userDto);
        }
		filterChain.doFilter(servletRequest, servletResponse);
    }
    
    @Override
    public void destroy() {
    	
    }
}
