package com.rdfs.framework.workflow.entity;

import java.io.Serializable;
import java.util.Date;

public class CwRunTask implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6468237086887085128L;
	private Integer id;
	private String businessKey;  //业务主键
	private CwProcessInfo processInfo; //流程
	private CwTaskNode taskNode; //当前节点
	private String status;  //是否活跃
	private String assigenee; //分配人
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
	public CwTaskNode getTaskNode() {
		return taskNode;
	}
	public void setTaskNode(CwTaskNode taskNode) {
		this.taskNode = taskNode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssigenee() {
		return assigenee;
	}
	public void setAssigenee(String assigenee) {
		this.assigenee = assigenee;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
