package com.rdfs.framework.auth.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 权限集管理
 *
 */
public class SyPermSet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5865598830938287593L;
	private Integer id;
	private String permName;
	private String permType;
	private String permDesc;
	private String permStatus;
	private Date createTime;
	private Date updateTime;
	private String createUser;
	
	@JsonIgnore
	private List<SyResource> items;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		this.permName = permName;
	}
	public String getPermType() {
		return permType;
	}
	public void setPermType(String permType) {
		this.permType = permType;
	}
	public String getPermDesc() {
		return permDesc;
	}
	public void setPermDesc(String permDesc) {
		this.permDesc = permDesc;
	}
	public String getPermStatus() {
		return permStatus;
	}
	public void setPermStatus(String permStatus) {
		this.permStatus = permStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public List<SyResource> getItems() {
		return items;
	}
	public void setItems(List<SyResource> items) {
		this.items = items;
	}
	
	
}
