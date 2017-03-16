package com.rdfs.framework.workflow.service;

import com.rdfs.framework.workflow.entity.CwNodeEvent;

public interface DelegateTask {

	String getBusinessKey();
	
	String getEventName();
	
	CwNodeEvent getNodeEvent();
	
	String getAssigenee();
	
}
