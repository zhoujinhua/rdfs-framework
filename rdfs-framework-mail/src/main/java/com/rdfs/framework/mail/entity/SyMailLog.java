package com.rdfs.framework.mail.entity;

import java.io.Serializable;
import java.util.Date;

public class SyMailLog implements Serializable{

	/**
	 * 邮件发送记录
	 */
	private static final long serialVersionUID = 2017148651718591300L;
	
	private Integer id;
	private String sender;
	private String receiver;
	private String annex;
	private String content;
	private String status;
	private String remark;
	private Date createTime;
	
	
	
	public SyMailLog(String sender, String receiver, String annex, String content, String status,
			String remark, Date createTime) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.annex = annex;
		this.content = content;
		this.status = status;
		this.remark = remark;
		this.createTime = createTime;
	}
	public SyMailLog() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getAnnex() {
		return annex;
	}
	public void setAnnex(String annex) {
		this.annex = annex;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
