package com.rdfs.framework.workflow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.service.ProcessInfoService;


@Controller
@RequestMapping("workflow")
public class WorkflowController {

	@Autowired
	private ProcessInfoService processInfoService;
	
	@RequestMapping("listFlow")
	@ResponseBody
	public Map<String, Object> listFlow(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = new HashMap<>();
		List<CwProcessInfo> list = processInfoService.getProcessInfos();
		map.put("aaData", list);
		return map;
	}
}
