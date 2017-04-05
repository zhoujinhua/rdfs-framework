package com.rdfs.framework.taglib.bean;

import java.io.Serializable;

public class DictItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3626224385128936360L;

	/**
	 * 流水号
	 */
	private Integer id;
	
	/**
	 * 字典名称
	 */
	private String name;
	
	/**
	 * 字典KEY
	 */
	private String key;
	
	/**
	 * 对照码
	 */
	private String code;
	
	/**
	 * 对照值
	 */
	private String desc;
	
	/**
	 * 是否启用
	 */
	private String status;
	
	/**
	 * 所属分组
	 */
	private String group;
	
	/**
	 * 排序号
	 */
	private Integer sortNo;
	
	/**
	 * 备注
	 */
	private String remark;
	

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
