package com.rdfs.framework.workflow.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CwTaskNode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4381495954514591167L;
	private Integer id;
	@JsonIgnore
	private CwProcessInfo processInfo; //流程
	private String name;
	private String code;
	private String type; //类型：开始/用户任务/结束
	
	@JsonIgnore
	private List<CwNodeEvent> nodeEvents;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<CwNodeEvent> getNodeEvents() {
		return nodeEvents;
	}
	public void setNodeEvents(List<CwNodeEvent> nodeEvents) {
		this.nodeEvents = nodeEvents;
	}
	
}
