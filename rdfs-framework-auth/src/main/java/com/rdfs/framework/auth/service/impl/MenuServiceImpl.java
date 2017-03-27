package com.rdfs.framework.auth.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rdfs.framework.auth.entity.SyMenu;
import com.rdfs.framework.auth.service.MenuService;
import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.service.TreeService;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;

@Service
public class MenuServiceImpl extends HibernateServiceSupport implements MenuService {

	@Autowired
	private TreeService treeService;
	
	@Override
	public List<TreeDto> menuTree() throws Exception {
		List<SyMenu> menuList = getList("from SyMenu");
		TreeDto treeDto = new TreeDto("0", "", "菜单树", true, false);
		List<TreeDto> list = treeService.getList(menuList, true, "menuId", "menuTitle", "parMenu.menuId", null);
		list.add(treeDto);
		return list;
	}

	@Override
	public Page pageList(SyMenu menu, Page page) {
		String hql = "";
		if(menu!=null && menu.getMenuId() != null && !"".equals(menu.getMenuId()) && !"0".equals(menu.getMenuId())){
			hql = "from SyMenu where parMenu.menuId = " + menu.getMenuId() + "";
		} else {
			hql = "from SyMenu where parMenu is null or parMenu.menuId is null";
		}
		return pageList(page, hql);
	}

	@Override
	public void saveMenu(SyMenu menu) {
		if(menu.getMenuId()!=null){
			updateEntity(menu, "menuTitle","menuIcon","status","sortNo");
		} else {
			menu.setCreateTime(new Date());
			saveEntity(menu);
		}
	}
}
