package com.rdfs.framework.auth.controller;

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

import com.rdfs.framework.auth.entity.SyResource;
import com.rdfs.framework.auth.service.ResourceService;
import com.rdfs.framework.cache.service.CacheResourceService;
import com.rdfs.framework.core.utils.ReturnUitl;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.utils.PageUtil;

@Controller
@RequestMapping("resource")
public class ResourceController {

	private Logger logger = LoggerFactory.getLogger(ResourceController.class);
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private CacheResourceService cacheResourceService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyResource resource){
		Map<String, Object> map = new HashMap<String,Object>();
		Page page = resourceService.pageList(resource, PageUtil.getPage(request));
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, SyResource resource){
		try{
			resourceService.saveResource(resource);
			cacheResourceService.cacheResource(resource);
			ReturnUitl.write(response, 1);
		} catch(Exception e){
			logger.error("保存功能信息失败,", e);
			ReturnUitl.write(response, 0, "保存功能信息失败,"+e.getMessage());
		}
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest reuqest, SyResource resource){
		resource = resourceService.getEntityById(SyResource.class, resource.getItemId(), true);
		
		reuqest.setAttribute("resource", resource);
		return "resource/add";
	}
}
