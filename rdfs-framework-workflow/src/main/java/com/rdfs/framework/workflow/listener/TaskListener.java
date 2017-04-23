package com.rdfs.framework.workflow.listener;

import com.rdfs.framework.workflow.entity.CwRunTask;

public interface TaskListener {

	void notify(CwRunTask task);
}
