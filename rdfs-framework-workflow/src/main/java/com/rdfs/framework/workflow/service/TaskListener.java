package com.rdfs.framework.workflow.service;

import com.rdfs.framework.workflow.entity.CwRunTask;

public interface TaskListener {

	void notify(CwRunTask task);
}
