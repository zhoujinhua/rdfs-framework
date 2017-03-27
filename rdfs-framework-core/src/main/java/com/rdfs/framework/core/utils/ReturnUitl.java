package com.rdfs.framework.core.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.rdfs.framework.core.bean.ApiResult;

public class ReturnUitl{
	
	/**
	 * 
	 * @param response
	 * @param status 0：成功  1：失败
	 */
	public static void write(HttpServletResponse response, int status, Object obj) {
		PrintWriter w = null;
		try {
			 w = response.getWriter();
			 ApiResult result = new ApiResult();
			 result.setAaData(obj);
			 result.setResponseCode(status);
			 w.print(JacksonUtil.toJson(result));
		} catch (IOException e) {
			 e.printStackTrace();
		}finally{
			if(w!=null)w.close();
		}
	}
	/**
	 * 
	 * @param response
	 * @param status 0：成功  1：失败
	 */
	public static void write(HttpServletResponse response, int status) {
		PrintWriter w = null;
		try {
			w = response.getWriter();
			ApiResult result = new ApiResult();
			result.setResponseCode(status);
			w.print(JacksonUtil.toJson(result));
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(w!=null)w.close();
		}
		
		
	}

	/**
	 * 
	 * @param response
	 * @param status
	 * @param resopnseMsg
	 */
	public static void write(HttpServletResponse response, int status,
			String resopnseMsg) {
		PrintWriter w = null;
		try {
			 w = response.getWriter();
			 ApiResult result = new ApiResult();
			result.setResponseCode(status);
			result.setResponseMsg(resopnseMsg);
			w.print(JacksonUtil.toJson(result));
		} catch (IOException e) {
			 e.printStackTrace();
		}finally{
			if(w!=null)w.close();
		}
		
	}
}
