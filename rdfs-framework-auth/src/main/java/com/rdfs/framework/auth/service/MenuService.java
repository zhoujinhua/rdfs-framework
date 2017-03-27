package com.rdfs.framework.auth.service;

import java.util.List;

import com.rdfs.framework.auth.entity.SyMenu;
import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.service.HibernateService;

public interface MenuService extends HibernateService{

	List<TreeDto> menuTree() throws Exception;

	Page pageList(SyMenu menu, Page page);

	void saveMenu(SyMenu menu);

}
