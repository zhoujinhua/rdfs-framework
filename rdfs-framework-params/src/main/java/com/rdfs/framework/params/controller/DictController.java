package com.rdfs.framework.params.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdfs.framework.cache.service.CacheParamsService;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.enums.OperMode;
import com.rdfs.framework.hibernate.utils.PageUtil;
import com.rdfs.framework.params.entity.SyDictItem;
import com.rdfs.framework.params.service.DictItemService;


@Controller
@RequestMapping("dict")
public class DictController {
	
    @Autowired
    private DictItemService dictItemService;
    
    @Autowired
    private CacheParamsService cacheParamsService;
    
    private Logger logger = LoggerFactory.getLogger(DictController.class);
    
    @RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyDictItem arg){
		Map<String, Object> map = new HashMap<String,Object>();
		Page page = dictItemService.pageList(arg, PageUtil.getPage(request), OperMode.LIKE, "name", "code", "status", "key", "desc", "group");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
    
    @RequestMapping("save")
    public String saveArgControl(HttpServletRequest request, SyDictItem dictItem) {
    	try{
    		dictItem = dictItemService.saveDictItem(dictItem);
    		cacheParamsService.cacheDict(dictItem);
    		request.setAttribute("msg", "操作成功");
    	} catch(Exception e){
    		logger.error("保存失败,错误信息：",e);
    		request.setAttribute("dict", dictItem);
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    		return "param/dict/add";
    	}
    	return "dict/list";
    }
    
    @RequestMapping("edit")
    public String edit(HttpServletRequest request, SyDictItem dictItem) {
    	dictItem = dictItemService.getEntityById(SyDictItem.class, dictItem.getId(), true);
    	request.setAttribute("dict", dictItem);
    	return "dict/add";
    }
    
    @RequestMapping("delete")
    public String deleteArgControls(HttpServletRequest request, SyDictItem dictItem) {
    	try{
    		dictItemService.deleteEntity(dictItem);
    		request.setAttribute("msg", "操作成功");
    	} catch(Exception e){
    		logger.error("停用/启用失败,错误信息：",e);
    		request.setAttribute("msg", "操作失败,"+e.getMessage());
    	}
    	return "dict/list";
    }
    
    @RequestMapping("detail")
    public String detail(HttpServletRequest request, SyDictItem dictItem) {
    	dictItem = dictItemService.getEntityById(SyDictItem.class, dictItem.getId(), true);

    	request.setAttribute("dict", dictItem);
    	return "dict/detail";
    }
    
}
