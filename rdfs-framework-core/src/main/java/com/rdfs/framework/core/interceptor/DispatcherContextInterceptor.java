package com.rdfs.framework.core.interceptor;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rdfs.framework.core.bean.UserDto;
import com.rdfs.framework.core.spring.SpringDispatcherContextHolder;
import com.rdfs.framework.core.utils.AuthUtil;

/**
 * 拦截没有登陆请求下访问方法
 */
public class DispatcherContextInterceptor implements HandlerInterceptor {
	
	//监控方法执行时间
	private NamedThreadLocal<Long>  startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");
	
	//出错返回页面
	private static final String redirectURL = "/error.jsp";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		long beginTime = System.currentTimeMillis();
        startTimeThreadLocal.set(beginTime);
		try {
			String juid = AuthUtil.getJuid(request);
			UserDto userDto = AuthUtil.getUserDto(juid);
			
			boolean flag = AuthUtil.compareUserDto(request);
	        if(!flag){
	        	response.sendError(405);
	        	System.out.println(new Date().toString() + "," + userDto.toString() + ",请求地址：" + request.getRequestURI() + ",请求状态："+response.getStatus());
				response.sendRedirect(request.getContextPath() + redirectURL);
	        } else {
	        	AuthUtil.heartbeat(juid); //维持心跳
	        	AuthUtil.setCurrentUserDto(userDto);
	        	System.out.println(new Date().toString() + "," + userDto.toString() + ",请求地址：" + request.getRequestURI() + ",请求状态："+response.getStatus());
	        }
			SpringDispatcherContextHolder.initDispatcherContext(response);
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
		SpringDispatcherContextHolder.resetDispatcherContext();
		long endTime = System.currentTimeMillis();
        long beginTime = startTimeThreadLocal.get();
        long consumeTime = endTime - beginTime;
        
        //处理时间超过500毫秒的请求为慢请求  
        //if(consumeTime > 500) {
        	System.out.println(String.format("%s consume %d millis", request.getRequestURI(), consumeTime));
        //}
        	
    	if(ex!=null){
    		ex.printStackTrace();
    	}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}
}
