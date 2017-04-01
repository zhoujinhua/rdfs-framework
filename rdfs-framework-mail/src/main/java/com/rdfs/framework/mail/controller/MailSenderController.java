package com.rdfs.framework.mail.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.enums.OperMode;
import com.rdfs.framework.hibernate.enums.OrderMode;
import com.rdfs.framework.hibernate.utils.PageUtil;
import com.rdfs.framework.mail.entity.SyMailSender;
import com.rdfs.framework.mail.service.MailSenderService;

@Controller
@RequestMapping("sender")
public class MailSenderController {

private Logger logger = LoggerFactory.getLogger(MailSenderController.class);
	
	@Autowired
	private MailSenderService mailSenderService;

	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyMailSender sender){
		Map<String, Object> map = new HashMap<String,Object>();
		Page page = mailSenderService.pageList(sender, PageUtil.getPage(request), OrderMode.DESC, "createTime", OperMode.LIKE, "userName", "email", "isDefault", "status");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request , SyMailSender sender){
		try{
			mailSenderService.saveMailSender(sender);
			request.setAttribute("msg", "保存发件人成功!");
		} catch(Exception e){
			logger.error("保存失败,",e);
			request.setAttribute("sender", sender);
			request.setAttribute("msg", "保存发件人失败,"+e.getMessage());
			
			return "sender/add";
		}
		return "mail/sender/list";
	}
	
	@RequestMapping("edit")
	public String add(HttpServletRequest request, SyMailSender sender){
		sender = mailSenderService.getEntityById(SyMailSender.class, sender.getId(), true);
		
		request.setAttribute("sender", sender);
		return "mail/sender/add";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request , SyMailSender sender){
		try{
			mailSenderService.deleteEntity(sender);
			request.setAttribute("msg", "删除发件人成功!");
		} catch(Exception e){
			logger.error("删除发件人失败,",e);
			request.setAttribute("msg", "删除发件人失败,"+e.getMessage());
		}
		return "mail/sender/list";
	}
	
	@RequestMapping("setDefault")
	public String setDefault(HttpServletRequest request , SyMailSender sender){
		try{
			mailSenderService.updateDefault(sender);
			request.setAttribute("msg", "设置默认成功!");
		} catch(Exception e){
			logger.error("设置状态失败,",e);
			request.setAttribute("msg", "设置默认失败,"+e.getMessage());
		}
		return "mail/sender/list";
	}
}
