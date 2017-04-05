package com.rdfs.framework.mail.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.enums.OperMode;
import com.rdfs.framework.hibernate.enums.OrderMode;
import com.rdfs.framework.hibernate.utils.PageUtil;
import com.rdfs.framework.mail.entity.SyMailLog;
import com.rdfs.framework.mail.service.MailSenderService;

@Controller
@RequestMapping("mailLog")
public class MailLogController {

	@Autowired
	private MailSenderService mailSenderService;

	@RequestMapping("list")
	@ResponseBody
	public Map<String,Object> list(HttpServletRequest request, SyMailLog log){
		Map<String, Object> map = new HashMap<String,Object>();
		Page page = mailSenderService.pageList(log, PageUtil.getPage(request), OrderMode.DESC, "createTime", OperMode.LIKE, "userName", "receiver", "status");
		
		map.put("aaData", page.getItems());
		map.put("recordsTotal", page.getCount());
	    map.put("recordsFiltered", page.getCount());
	    
	    return map;
	}
}
