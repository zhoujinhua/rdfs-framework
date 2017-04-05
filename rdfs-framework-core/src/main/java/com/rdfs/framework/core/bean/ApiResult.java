package com.rdfs.framework.core.bean;

import java.io.Serializable;

public class ApiResult implements Serializable{

	/**
	 * 公共的返回dto
	 */
	private static final long serialVersionUID = 1L;

	private Integer responseCode;
	
	private boolean success;
	
	private String responseMsg;
	
	private Object aaData;

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getAaData() {
		return aaData;
	}

	public void setAaData(Object aaData) {
		this.aaData = aaData;
	}

}
