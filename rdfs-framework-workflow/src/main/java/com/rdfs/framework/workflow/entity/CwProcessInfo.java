package com.rdfs.framework.workflow.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 流程定义
 * @author zhoufei
 *
 */
public class CwProcessInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 711291978400640436L;
	private Integer id;
	private String name;
	private String code;
	private Long version;
	private String status; 
	private Date createTime;
	
	@JsonIgnore
	private List<CwTaskNode> taskNodes;
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public List<CwTaskNode> getTaskNodes() {
		return taskNodes;
	}
	public void setTaskNodes(List<CwTaskNode> taskNodes) {
		this.taskNodes = taskNodes;
	}
	
}
