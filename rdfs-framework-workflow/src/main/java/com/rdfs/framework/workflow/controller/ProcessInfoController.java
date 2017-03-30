package com.rdfs.framework.workflow.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.utils.JacksonUtil;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.enums.OperMode;
import com.rdfs.framework.hibernate.enums.OrderMode;
import com.rdfs.framework.hibernate.utils.PageUtil;
import com.rdfs.framework.workflow.entity.CwProcessInfo;
import com.rdfs.framework.workflow.service.ProcessInfoService;

@Controller
@RequestMapping("process")
public class ProcessInfoController {

	@Autowired
	private ProcessInfoService processInfoService;
	
	private Logger logger = LoggerFactory.getLogger(ProcessInfoController.class);
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> listFlow(HttpServletRequest request, CwProcessInfo processInfo){
		Map<String,Object> map = new HashMap<>();
		
		Page page = processInfoService.pageList(processInfo, PageUtil.getPage(request), OrderMode.DESC, "createTime", OperMode.LIKE, "code","name","status");
		map.put("aaData", page.getItems());
        map.put("recordsTotal", page.getCount());	
	    map.put("recordsFiltered", page.getCount());
		return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, CwProcessInfo processInfo){
		processInfo = processInfoService.getEntityById(CwProcessInfo.class, processInfo.getId(), false);
		request.setAttribute("process", processInfo);
		return "workflow/process/add";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, CwProcessInfo processInfo){
		try{
			processInfoService.saveProcessInfo(processInfo);
			request.setAttribute("msg", "保存流程成功!");
		}catch(Exception e){
			logger.error("保存流程失败,", e);
			request.setAttribute("msg", "保存流程失败,"+e.getMessage());
			request.setAttribute("process", processInfo);
			return "workflow/process/add";
		}
		return "workflow/process/list";
	}
	
	@RequestMapping("addVersion")
	public String addVersion(HttpServletRequest request, CwProcessInfo processInfo){
		try{
			processInfoService.updateProcessVersion(processInfo);
			request.setAttribute("msg", "更新流程版本号成功!");
		}catch(Exception e){
			logger.error("更新流程版本失败,", e);
			request.setAttribute("msg", "更新流程版本失败,"+e.getMessage());
		}
		return "workflow/process/list";
	}
	
	@RequestMapping("copy")
	public String copy(HttpServletRequest request, CwProcessInfo processInfo){
		try{
			
			request.setAttribute("msg", "复制流程成功!");
		}catch(Exception e){
			logger.error("复制流程失败,", e);
			request.setAttribute("msg", "复制流程失败,"+e.getMessage());
		}
		return "workflow/process/list";
	}

	@RequestMapping("nodeTree")
	public void nodeTree(HttpServletRequest request, HttpServletResponse response, CwProcessInfo processInfo){
		PrintWriter pw = null;
		try{
			pw = response.getWriter();
			List<TreeDto> treeList = processInfoService.nodeTree(processInfo);
			pw.write(JacksonUtil.toJson(treeList));
		}catch(Exception e){
			logger.error("封装菜单树失败,", e);
			e.printStackTrace();
		}
	}
}
