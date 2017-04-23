package com.rdfs.framework.workflow.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdfs.framework.cache.service.CacheWorkflowService;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.enums.OperMode;
import com.rdfs.framework.hibernate.utils.PageUtil;
import com.rdfs.framework.hibernate.utils.ReturnUitl;
import com.rdfs.framework.workflow.entity.CwNodeEvent;
import com.rdfs.framework.workflow.service.NodeEventService;

@Controller
@RequestMapping("event")
public class NodeEventController {

	@Autowired
	private NodeEventService nodeEventService;
	
	@Autowired
	private CacheWorkflowService cacheWorkflowService;
	
	private Logger logger = LoggerFactory.getLogger(NodeEventController.class);
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> listFlow(HttpServletRequest request, CwNodeEvent nodeEvent){
		Map<String,Object> map = new HashMap<>();
		
		Page page = nodeEventService.pageList(nodeEvent, PageUtil.getPage(request), OperMode.LIKE, "code", "name", "currNode.id");
		map.put("aaData", page.getItems());
        map.put("recordsTotal", page.getCount());	
	    map.put("recordsFiltered", page.getCount());
		return map;
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, CwNodeEvent nodeEvent){
		nodeEvent = nodeEventService.getEntityById(CwNodeEvent.class, nodeEvent.getId(), true);
		
		request.setAttribute("id", nodeEvent.getCurrNode().getId());
		request.setAttribute("name", nodeEvent.getCurrNode().getName());
		request.setAttribute("event", nodeEvent);
		return "workflow/event/add";
	}
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, CwNodeEvent nodeEvent){
		nodeEvent = nodeEventService.getEntityById(CwNodeEvent.class, nodeEvent.getId(), true);
		
		request.setAttribute("event", nodeEvent);
		return "workflow/event/detail";
	}
	
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, CwNodeEvent nodeEvent){
		try{
			nodeEventService.saveNodeEvent(nodeEvent);
			cacheWorkflowService.cacheNodeEvent(nodeEvent);
			ReturnUitl.write(response, 1, nodeEvent);
		}catch(Exception e){
			logger.error("保存节点事件失败,", e);
			ReturnUitl.write(response, 1, "保存节点事件失败,"+e.getMessage());
		}
	}
	
	@RequestMapping("delete")
	public void delete(HttpServletRequest request, HttpServletResponse response, CwNodeEvent nodeEvent){
		try{
			CwNodeEvent cwNodeEvent = new CwNodeEvent();
			cwNodeEvent.setId(nodeEvent.getId());
			
			
			nodeEventService.deleteNodeEvent(nodeEvent);
			cacheWorkflowService.delNodeEvent(cwNodeEvent);
			ReturnUitl.write(response, 1);
		}catch(Exception e){
			logger.error("删除节点事件失败,", e);
			ReturnUitl.write(response, 0, "删除节点事件失败,"+e.getMessage());
		}
	}
	
}
