package com.rdfs.framework.core.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rdfs.framework.core.bean.UserDto;
import com.rdfs.framework.core.spring.SpringDispatcherContextHolder;
import com.rdfs.framework.core.utils.AuthUtil;

/**
 * 拦截没有登陆请求下访问方法
 * @author shaow
 *
 */
public class DispatcherContextInterceptor extends
		HandlerPreparInterceptor {
	
	/**/
	//出错返回页面
	private String redirectURL = "/error.jsp";
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) {
		
		try {
			String juid = AuthUtil.getJuid(request);
			UserDto userDto = AuthUtil.getUserDto(juid);
			
			boolean flag = AuthUtil.compareUserDto(request);
	        if(!flag){
	        	response.sendError(405);
				response.sendRedirect(request.getContextPath() + redirectURL);
	        } else {
	        	AuthUtil.heartbeat(juid); //维持心跳
	        	AuthUtil.setCurrentUserDto(userDto);
	        }
			SpringDispatcherContextHolder.initDispatcherContext(response);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		SpringDispatcherContextHolder.resetDispatcherContext();
	}
}
