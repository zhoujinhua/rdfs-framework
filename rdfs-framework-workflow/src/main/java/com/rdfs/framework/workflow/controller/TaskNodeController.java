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
import com.rdfs.framework.core.utils.ReturnUitl;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.enums.OperMode;
import com.rdfs.framework.hibernate.utils.PageUtil;
import com.rdfs.framework.workflow.entity.CwTaskNode;
import com.rdfs.framework.workflow.service.TaskNodeService;

@Controller
@RequestMapping("node")
public class TaskNodeController {

	@Autowired
	private TaskNodeService taskNodeService;
	
	private Logger logger = LoggerFactory.getLogger(TaskNodeController.class);
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> listFlow(HttpServletRequest request, CwTaskNode taskNode){
		Map<String,Object> map = new HashMap<>();
		
		Page page = taskNodeService.pageList(taskNode, PageUtil.getPage(request), OperMode.LIKE, "code","name","processInfo.id");
		map.put("aaData", page.getItems());
        map.put("recordsTotal", page.getCount());	
	    map.put("recordsFiltered", page.getCount());
		return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, CwTaskNode taskNode){
		taskNode = taskNodeService.getEntityById(CwTaskNode.class, taskNode.getId(), false);
		
		request.setAttribute("id", taskNode.getProcessInfo().getId());
		request.setAttribute("node", taskNode);
		return "workflow/node/add";
	}
	
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, CwTaskNode taskNode){
		try{
			taskNodeService.saveTaskNode(taskNode);
			ReturnUitl.write(response, 1, taskNode);
		}catch(Exception e){
			logger.error("保存节点信息失败,", e);
			ReturnUitl.write(response, 0, "保存节点信息失败,"+e.getMessage());
		}
	}
	
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, CwTaskNode taskNode){
		try{
			taskNodeService.deleteTaskNode(taskNode);
			ReturnUitl.write(response, 1);
		}catch(Exception e){
			logger.error("删除节点失败,", e);
			ReturnUitl.write(response, 0, "删除节点失败,"+e.getMessage());
		}
	}
	
	@RequestMapping("nodeTree")
	public void nodeTree(HttpServletRequest request, HttpServletResponse response, CwTaskNode taskNode){
		PrintWriter pw = null;
		try{
			pw = response.getWriter();
			List<TreeDto> treeList = taskNodeService.nodeTree(taskNode);
			pw.print(JacksonUtil.toJson(treeList));
		}catch(Exception e){
			logger.error("封装节点树失败,", e);
		}
	}
}
