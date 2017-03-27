package com.rdfs.framework.quartz.entity;

import java.io.Serializable;

public class QzJobRelation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5881787576523075825L;
	private Integer id;
	private QzJobInfo jobInfo;
	private Integer seq;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public QzJobInfo getJobInfo() {
		return jobInfo;
	}
	public void setJobInfo(QzJobInfo jobInfo) {
		this.jobInfo = jobInfo;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
}
