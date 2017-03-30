package com.rdfs.framework.workflow.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rdfs.framework.core.utils.ReturnUitl;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.service.TaskMonitorService;
import com.rdfs.framework.workflow.service.TaskNodeService;

@Controller
@RequestMapping("monitor")
public class TaskMonitorController {

	private Logger logger = LoggerFactory.getLogger(TaskMonitorController.class);
	
	@Autowired
	private TaskMonitorService taskMonitorService;
	
	@Autowired
	private TaskNodeService taskNodeService;
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, CwNodeEvent nodeEvent){
		nodeEvent = taskNodeService.getEntityById(CwNodeEvent.class, nodeEvent.getId(), true);
		request.setAttribute("nodeEvent", nodeEvent);
		return "workflow/monitor/add";
	}
	
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, CwNodeEvent nodeEvent){
		try{
			taskMonitorService.saveTaskMonitor(nodeEvent);
			ReturnUitl.write(response, 1);
		}catch(Exception e){
			logger.error("保存事件监听失败,", e);
			ReturnUitl.write(response, 0, "保存事件监听失败,"+e.getMessage());
		}
	}
}
