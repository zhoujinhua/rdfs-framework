package com.rdfs.framework.quartz.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class QzJobInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3531845846363888016L;
	private Integer id;
	private String name; 
	private String group;
	private String cron;
	private String status;
	private String runStatus; //运行状态
	private String type;
	private String batch;
	private String sendMail;
	private String email;
	private String ip;
	private String remark;
	private Date createTime;
	
	private String httpUrl;
	private String className;
	
	private String host;
	private String userName;
	private String password;
	private Integer port;
	private String cmd;
	
	@JsonIgnore
	private List<QzJobRelation> relations;
	@JsonIgnore
	private String relationName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRunStatus() {
		return runStatus;
	}
	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getSendMail() {
		return sendMail;
	}
	public void setSendMail(String sendMail) {
		this.sendMail = sendMail;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	public List<QzJobRelation> getRelations() {
		return relations;
	}
	public void setRelations(List<QzJobRelation> relations) {
		this.relations = relations;
	}
	public String getRelationName() {
		if(this.relations != null && !this.relations.isEmpty()){
			String name = "";
			for(QzJobRelation jobRelation : this.relations){
				name += jobRelation.getJobInfo().getName() + ",";
			}
			return name.substring(0, name.length()-1);
		}
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	
	
}
