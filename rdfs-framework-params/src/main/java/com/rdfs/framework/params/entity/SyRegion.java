package com.rdfs.framework.params.entity;

import com.rdfs.framework.taglib.bean.Region;

/**
 * 省市区域
 */

public class SyRegion extends Region implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5304643791677673937L;
	private String regCode;
	private String regName;
	private String regCodePar;
	private String regLevel;
	private String memo;

	public SyRegion() {
		
	}

	public SyRegion(String regCode, String regName, String regLevel) {
		this.regCode = regCode;
		this.regName = regName;
		this.regLevel = regLevel;
	}

	public String getRegCode() {
		return this.regCode;
	}

	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}

	public String getRegName() {
		return this.regName;
	}

	public void setRegName(String regName) {
		this.regName = regName;
	}

	public String getRegCodePar() {
		return this.regCodePar;
	}

	public void setRegCodePar(String regCodePar) {
		this.regCodePar = regCodePar;
	}

	public String getRegLevel() {
		return this.regLevel;
	}

	public void setRegLevel(String regLevel) {
		this.regLevel = regLevel;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}