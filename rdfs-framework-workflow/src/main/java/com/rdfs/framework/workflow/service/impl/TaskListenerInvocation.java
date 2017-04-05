package com.rdfs.framework.workflow.service.impl;

import com.rdfs.framework.workflow.entity.CwRunTask;
import com.rdfs.framework.workflow.listener.TaskListener;

public class TaskListenerInvocation {

	protected final TaskListener taskListenerInstance;
	protected final CwRunTask task;
	
	public TaskListenerInvocation(TaskListener taskListenerInstance, CwRunTask task) {
		this.taskListenerInstance = taskListenerInstance;
		this.task = task;
	}

	protected void invoke() throws Exception {
		taskListenerInstance.notify(task);
	}
}
