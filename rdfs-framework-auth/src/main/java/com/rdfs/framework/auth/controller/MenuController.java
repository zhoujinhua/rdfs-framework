package com.rdfs.framework.auth.controller;

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

import com.rdfs.framework.auth.entity.SyMenu;
import com.rdfs.framework.auth.service.MenuService;
import com.rdfs.framework.cache.service.CacheResourceService;
import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.utils.JacksonUtil;
import com.rdfs.framework.core.utils.ReturnUitl;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.utils.PageUtil;

@Controller
@RequestMapping("menu")
public class MenuController {

	private Logger logger = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private CacheResourceService cacheResourceService;
	
	@Autowired
	private MenuService menuService;
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyMenu menu){
		Map<String, Object> map = new HashMap<String,Object>();
		Page page = menuService.pageList(menu, PageUtil.getPage(request) );
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("menuTree")
	public void menuTree(HttpServletRequest request, HttpServletResponse response){
		PrintWriter pw = null;
		try{
			pw = response.getWriter();
			List<TreeDto> treeList = menuService.menuTree();
			pw.write(JacksonUtil.toJson(treeList));
		}catch(Exception e){
			logger.error("封装菜单树失败,", e);
		}
	}
	
	@RequestMapping("save")
	public void save(HttpServletRequest request, HttpServletResponse response, SyMenu menu){
		try{
			menuService.saveMenu(menu);
			cacheResourceService.cacheMenu(menu);
			ReturnUitl.write(response, 1);
		}catch(Exception e){
			logger.error("保存菜单信息异常,", e);
			ReturnUitl.write(response, 0, "保存功能信息失败,"+e.getMessage());
		}
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, SyMenu menu){
		menu = menuService.getEntityById(SyMenu.class, menu.getMenuId(), true);
		request.setAttribute("menu", menu);
		return "menu/add";
	}
}
