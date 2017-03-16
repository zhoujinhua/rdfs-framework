package com.rdfs.framework.workflow.entity;

import java.io.Serializable;

public class CwTaskMonitor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3264469814131569954L;
	private Integer id;
	private String eventName;     //事件监听只有"complete"事件
	private String className; //监听类
	private Integer eventId;  //目前只支持事件监听
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Integer getEventId() {
		return eventId;
	}
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
}
