package com.rdfs.framework.workflow.entity;

import java.io.Serializable;
import java.util.Date;

public class CwRunExecution implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2530240011528405212L;
	private Integer id;
	private CwProcessInfo processInfo;
	private String businessKey;
	private CwNodeEvent nodeEvent;
	private String userId;  //操作人
	private Date createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CwProcessInfo getProcessInfo() {
		return processInfo;
	}
	public void setProcessInfo(CwProcessInfo processInfo) {
		this.processInfo = processInfo;
	}
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public CwNodeEvent getNodeEvent() {
		return nodeEvent;
	}
	public void setNodeEvent(CwNodeEvent nodeEvent) {
		this.nodeEvent = nodeEvent;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
