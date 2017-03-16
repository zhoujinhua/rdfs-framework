package com.rdfs.framework.workflow.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.entity.CwRunTask;
import com.rdfs.framework.workflow.entity.CwTaskMonitor;
import com.rdfs.framework.workflow.service.TaskListener;
import com.rdfs.framework.workflow.service.TaskMonitorService;

@Service
public class TaskMonitorServiceImpl extends HibernateServiceSupport implements TaskMonitorService {

	@Override
	public void invoke(CwRunTask runTask, CwNodeEvent nodeEvent, String eventName) throws Exception {
		//唤起事件监听
		List<CwTaskMonitor> list = getList("from CwTaskMonitor where eventName = '" + eventName + "' and eventId = " + nodeEvent.getId());
		if(list!=null && !list.isEmpty()){
			for(CwTaskMonitor monitor : list){
				Class<?> clazz = Class.forName(monitor.getClassName());
				Object taskListenerInstance = clazz.newInstance();
				if(taskListenerInstance instanceof TaskListener){
					new TaskListenerInvocation((TaskListener)taskListenerInstance, runTask).invoke();
				}
			}
		}
	}

}
