package com.rdfs.framework.hibernate.bean;

import java.util.List;


/**
 * 分页Page对象
 */
public class Page {

	private int count; //总记录数
	private int limit ; //每页记录数
	private int start;   //起始记录
	private List<?> items; //记录内容
	
	
	public Page() {
		super();
	}
	
	public Page(int limit, int start) {
		super();
		this.limit = limit;
		this.start = start;
	}

	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setStart(int start) {
		this.start = start;
	}
	public int getStart() {
		return start;
	}

	public List<?> getItems() {
		return items;
	}
	public void setItems(List<?> items) {
		this.items = items;
	}
}