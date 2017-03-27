package com.rdfs.framework.auth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rdfs.framework.auth.entity.SyMenu;
import com.rdfs.framework.auth.entity.SyPermSet;
import com.rdfs.framework.auth.entity.SyResource;
import com.rdfs.framework.auth.service.PermSetService;
import com.rdfs.framework.cache.service.CacheResourceService;
import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.bean.UserDto;
import com.rdfs.framework.core.service.TreeService;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;

@Service
public class PermSetServiceImpl extends HibernateServiceSupport implements PermSetService {

	@Autowired
	private CacheResourceService cacheResourceService;
	
	@Autowired
	private TreeService treeService;
	
	/*@Override
	public List<TreeDto> getPermSetTree(SyUser user) throws Exception {
		user = getEntityByCode(SyUser.class,user.getUserId(),true);
		SyPermSet permset = new SyPermSet();
		permset.setPermStatus(Constants.IS.YES);
		
		List<SyPermSet> permSets = getList(permset,OperMode.EQ, "permStatus");
		List<TreeDto> treeList = treeService.getList(permSets, "id", "permName", null, user.getPermSets());
		return treeList;
	}*/

	@Override
	public void updatePermSet(SyPermSet permSet, String[] split) {
		List<SyResource> itemList = new ArrayList<SyResource>();
		for(String part : split){
			itemList.add(getSession().find(SyResource.class, Integer.parseInt(part)));
		}
		permSet.setItems(itemList);
		updateEntity(permSet,"items");
	}

	@Override
	public List<TreeDto> getMenuTree(SyPermSet permSet) throws Exception {
		permSet = getSession().find(SyPermSet.class, permSet.getId());
		List<SyMenu> menuList = cacheResourceService.getMenuList();
		if(menuList==null || menuList.isEmpty()){
			menuList = getSession().getCriteria(SyMenu.class).getResultList();
		}
		
		List<SyResource> itemList = cacheResourceService.getResourceList();
		if(itemList==null || itemList.isEmpty()){
			getSession().getCriteria(SyResource.class).getResultList();
		}
		List<TreeDto> treeList = treeService.getList(menuList, true, "menuId", "menuTitle", "parMenu.menuId", null);
		treeList.addAll(treeService.getList(itemList, false, "itemId", "itemTitle", "menu.menuId", permSet.getItems()));
		return treeList;
	}

	@Override
	public void savePermSet(SyPermSet permSet, UserDto user) {
		if(permSet.getId()!=null){
			permSet.setUpdateTime(new Date());
			updateEntity(permSet, "permStatus","permName","updateTime","permDesc");
		} else {
			permSet.setCreateTime(new Date());
			permSet.setCreateUser(user.getTrueName());
			
			saveEntity(permSet);
		}		
	}

}
