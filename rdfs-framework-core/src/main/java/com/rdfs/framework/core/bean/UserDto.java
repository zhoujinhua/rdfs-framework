package com.rdfs.framework.core.bean;

import java.io.Serializable;

public class UserDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7937470720257852491L;

	/**
	 * 登录用户ID
	 */
	private String userId;
	
	/**
	 * 随机用户标识
	 */
	private String juid;
	
	/**
	 * 登录名
	 */
	private String userName;
	
	/**
	 * 真实姓名
	 */
	private String trueName;
	
	/**
	 * 用户类型
	 */
	private String userType;
	
	/**
	 * 性别
	 */
	private String sex;
	
	/**
	 * 用户状态
	 */
	private String status;
	
	/**
	 * 客户端IP
	 */
	private String clientIp;
	
	/**
	 * 客户端指纹
	 */
	private String clientKey;
	
	/**
	 * 登录时间
	 */
	private String loginTime;

	public String getJuid() {
		return juid;
	}

	public void setJuid(String juid) {
		this.juid = juid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "当前用户 [用户ID=" + userId + ", 用户token=" + juid + ", 登录名=" + userName + ", 真实姓名=" + trueName
				+ ", 用户类型=" + userType + ", 用户性别=" + sex + ", 用户状态=" + status + ", 用户IP地址="
				+ clientIp + ", 用户客户端指纹=" + clientKey + ", 用户登录时间=" + loginTime + "]";
	}
	
	
}
