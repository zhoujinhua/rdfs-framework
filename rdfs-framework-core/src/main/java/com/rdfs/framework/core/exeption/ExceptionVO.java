package com.rdfs.framework.core.exeption;

/**
 * <b>异常信息VO</b>
 */
public class ExceptionVO {
	
	/**
	 * 异常编号
	 */
	private String id;
	
	/**
	 * 异常摘要信息
	 */
	private String info;
	
	/**
	 * 异常排查建议
	 */
	private String suggest;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}
}
