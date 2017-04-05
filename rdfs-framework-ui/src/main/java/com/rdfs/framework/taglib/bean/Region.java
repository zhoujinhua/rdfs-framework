package com.rdfs.framework.taglib.bean;

import java.io.Serializable;

public class Region implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3393376417315822535L;
	private String regCode;
	private String regName;
	private String regCodePar;
	private String regLevel;
	private String memo;
	
	public String getRegCode() {
		return regCode;
	}
	public void setRegCode(String regCode) {
		this.regCode = regCode;
	}
	public String getRegName() {
		return regName;
	}
	public void setRegName(String regName) {
		this.regName = regName;
	}
	public String getRegCodePar() {
		return regCodePar;
	}
	public void setRegCodePar(String regCodePar) {
		this.regCodePar = regCodePar;
	}
	public String getRegLevel() {
		return regLevel;
	}
	public void setRegLevel(String regLevel) {
		this.regLevel = regLevel;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
