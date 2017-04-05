package com.rdfs.framework.quartz.entity;

import java.io.Serializable;
import java.util.Date;

public class QzJobLog implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5866881777227607875L;

	private Integer id;
	private QzJobInfo jobInfo;
	private String isSuccess;
	private String message;
	private Date createTime;
	
	public QzJobLog() {
		super();
	}
	public QzJobLog(QzJobInfo jobInfo, String isSuccess, Date createTime) {
		super();
		this.jobInfo = jobInfo;
		this.isSuccess = isSuccess;
		this.createTime = createTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public QzJobInfo getJobInfo() {
		return jobInfo;
	}
	public void setJobInfo(QzJobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}
	public String getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
