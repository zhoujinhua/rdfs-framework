package com.rdfs.framework.workflow.entity;

import java.io.Serializable;

public class CwNodeEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1400463363125534803L;
	private Integer id;
	private CwTaskNode currNode; //上一节点
	private String action;   //动作
	private String route;   //事件完成后状态
	private CwTaskNode nextNode; //下一节点
	private String group;  //分配组
	private String user;   //分配人
	private String remark;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CwTaskNode getCurrNode() {
		return currNode;
	}
	public void setCurrNode(CwTaskNode currNode) {
		this.currNode = currNode;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public CwTaskNode getNextNode() {
		return nextNode;
	}
	public void setNextNode(CwTaskNode nextNode) {
		this.nextNode = nextNode;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
