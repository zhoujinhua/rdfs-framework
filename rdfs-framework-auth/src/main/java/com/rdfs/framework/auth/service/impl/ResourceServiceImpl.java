package com.rdfs.framework.auth.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.rdfs.framework.auth.entity.SyResource;
import com.rdfs.framework.auth.service.ResourceService;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;

@Service
public class ResourceServiceImpl extends HibernateServiceSupport implements ResourceService {

	@Override
	public Page pageList(SyResource resource, Page page) {
		String hql = "";
		if(resource.getMenu()!=null && resource.getMenu().getMenuId()!=null && 
				!resource.getMenu().getMenuId().equals("")){
			hql = "from SyResource where menu.menuId = " + resource.getMenu().getMenuId() + "";
		} else {
			hql = "from SyResource where status = '9'";
		}
		return pageList(page, hql);
	}

	@Override
	public void saveResource(SyResource resource) {
		if(resource.getItemId()!=null){
			updateEntity(resource, "itemTitle", "status", "itemLocation", "itemIcon", "sortNo");
		} else {
			resource.setCreateTime(new Date());
			saveEntity(resource);
		}
	}

}
