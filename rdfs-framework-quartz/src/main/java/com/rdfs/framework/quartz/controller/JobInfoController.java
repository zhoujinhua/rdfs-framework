package com.rdfs.framework.quartz.controller;

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
import com.rdfs.framework.quartz.entity.QzJobInfo;
import com.rdfs.framework.quartz.service.JobInfoService;

@Controller
@RequestMapping("job")
public class JobInfoController {

	@Autowired
	private JobInfoService jobInfoService;
	
	private Logger logger = LoggerFactory.getLogger(JobInfoController.class);
	
	@RequestMapping("list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, QzJobInfo job){
		Map<String, Object> map = new HashMap<>();
		
		Page page = jobInfoService.pageList(job, PageUtil.getPage(request), OrderMode.DESC, "createTime", OperMode.EQ, "name", "type", "status");
		map.put("aaData", page.getItems());
        map.put("recordsTotal", page.getCount());	
	    map.put("recordsFiltered", page.getCount());
		return map;
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request, QzJobInfo job){
		try{
			jobInfoService.saveJobInfo(job);
			request.setAttribute("msg", "保存定时调度任务成功.");
		}catch(Exception e){
			request.setAttribute("info", job);
			request.setAttribute("msg", "保存定时调度任务失败,"+e.getMessage());
			logger.error("保存定时调度任务失败,", e);
		}
		return "quartz/list";
	}
	
	@RequestMapping("edit")
	public String edit(HttpServletRequest request, QzJobInfo job){
		job = jobInfoService.getEntityById(QzJobInfo.class, job.getId(), true);
		request.setAttribute("info", job);
		return "quartz/add";
	}
	
	@RequestMapping("detail")
	public String detail(HttpServletRequest request, QzJobInfo job){
		job = jobInfoService.getEntityById(QzJobInfo.class, job.getId(), true);
		request.setAttribute("info", job);
		return "quartz/detail";
	}
	
	@RequestMapping("run")
	public String run(HttpServletRequest request, QzJobInfo job){
		job = jobInfoService.getEntityById(QzJobInfo.class, job.getId(), true);
		
		jobInfoService.runJob(job);
		request.setAttribute("msg", "任务调起成功!");
		return "quartz/list";
	}
	
}
