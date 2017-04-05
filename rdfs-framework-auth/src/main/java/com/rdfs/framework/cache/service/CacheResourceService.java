package com.rdfs.framework.cache.service;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import com.rdfs.framework.auth.entity.SyMenu;
import com.rdfs.framework.auth.entity.SyPermSet;
import com.rdfs.framework.auth.entity.SyResource;
import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.hibernate.service.HibernateService;

/**
 * 缓存菜单项 
 */
public interface CacheResourceService extends HibernateService{

	void cacheResourceData();

	SyResource getResource(String itemId);
	
	void cacheResource(SyResource resource);

	void cacheMenuData();

	SyMenu getMenu(String menuId);

	void cacheMenu(SyMenu menu);
	
	List<SyMenu> getMenuList();

	List<SyResource> getResourceList();

	void cachePermsetData();

	List<SyPermSet> getPermsetList();

	void cachePermset(SyPermSet permSet);
	
	/**
	 * 获取有序的用户可见菜单
	 * @param permSets
	 * @return
	 */
	Map<String, SortedMap<Integer, List<TreeDto>>> getMenuTree(List<SyPermSet> permSets);

	
}
