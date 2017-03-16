package com.rdfs.framework.params.entity;

import java.io.Serializable;

/**
 * 参数表
 *
 */

public class SyParameter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2293114383040530175L;

	/**
	 * 流水号
	 */
	private Integer id;
	
	/**
	 * 参数名称
	 */
	private String name;
	
	/**
	 * 参数键
	 */
	private String key;
	
	/**
	 * 参数值
	 */
	private String value;
	
	/**
	 * 分组
	 */
	private String group;
	
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
