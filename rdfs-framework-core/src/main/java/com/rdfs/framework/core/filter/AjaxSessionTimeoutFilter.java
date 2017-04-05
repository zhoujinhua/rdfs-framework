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

import com.rdfs.framework.core.utils.AuthUtil;

/**
 * 解决超时访问问题
 *
 */
public class AjaxSessionTimeoutFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		boolean flag = AuthUtil.compareUserDto(req);
		if(!flag){
			/*if (req.getHeader("x-requested-with") != null&& req.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
				res.setStatus(911);
			} else {*/
				chain.doFilter(req, res);
			/*}*/
		} else {
			chain.doFilter(req, res);
		}
	}
	
	public void destroy() {
		
	}

	public void init(FilterConfig chain) throws ServletException {

	}
}