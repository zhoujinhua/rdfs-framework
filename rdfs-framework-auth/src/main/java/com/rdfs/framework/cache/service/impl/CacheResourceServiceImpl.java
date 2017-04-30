package com.rdfs.framework.cache.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.rdfs.framework.auth.constants.AuthConstants;
import com.rdfs.framework.auth.entity.SyMenu;
import com.rdfs.framework.auth.entity.SyPermSet;
import com.rdfs.framework.auth.entity.SyResource;
import com.rdfs.framework.cache.service.CacheResourceService;
import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.core.redis.JedisUtil;
import com.rdfs.framework.core.utils.JsonUtil;
import com.rdfs.framework.core.utils.RdfsUtils;
import com.rdfs.framework.core.utils.StringUtils;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.hibernate.utils.JacksonUtil;

import redis.clients.jedis.Jedis;

@Service
public class CacheResourceServiceImpl extends HibernateServiceSupport implements CacheResourceService {

	private static Logger log = LoggerFactory.getLogger(CacheResourceServiceImpl.class);
	
	@Override
	public void cacheMenuData(){
		List<SyMenu> list = getList("from SyMenu ");
		if(list!=null && !list.isEmpty()){
			Jedis jedis = JedisUtil.getJedisClient();
			for(SyMenu menu : list){
				jedis.hset(AuthConstants.KEYS.MENU_KEY, menu.getMenuId()+"", JsonUtil.toJson(menu));
			}
			JedisUtil.close(jedis);
		}
	}
	
	@Override
	public void cachePermsetData(){
		List<SyPermSet> list = getList("from SyPermSet where permStatus = '" + Constants.IS.YES + "'");
		if(list!=null && !list.isEmpty()){
			Jedis jedis = JedisUtil.getJedisClient();
			for(SyPermSet permset : list){
				jedis.hset(AuthConstants.KEYS.PERMSET_KEY, permset.getId()+"", JsonUtil.toJson(permset));
			}
			JedisUtil.close(jedis);
		}
	}
	
	@Override
	public void cacheResourceData(){
		List<SyResource> list = getList("from SyResource");
		if(list!=null && !list.isEmpty()){
			Jedis jedis = JedisUtil.getJedisClient();
			for(SyResource resource : list){
				jedis.hset(AuthConstants.KEYS.RESOURCE_KEY, resource.getItemId()+"", JsonUtil.toJson(resource));
			}
			JedisUtil.close(jedis);
		}
	}
	
	@Override
	public SyMenu getMenu(String menuId){
		if (RdfsUtils.isEmpty(menuId)) {
			log.error("获取菜单项信息失败：菜单项ID不能为空。");
			return null;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String proString = jedis.hget(AuthConstants.KEYS.MENU_KEY, menuId);
		JedisUtil.close(jedis);
		if(!StringUtils.isBlank(proString)){
			return JacksonUtil.fromJson(proString, SyMenu.class);
		}
		return null;
	}
	
	@Override
	public SyResource getResource(String itemId){
		if (RdfsUtils.isEmpty(itemId)) {
			log.error("获取菜单项信息失败：菜单项ID不能为空。");
			return null;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String proString = jedis.hget(AuthConstants.KEYS.RESOURCE_KEY, itemId);
		JedisUtil.close(jedis);
		if(!StringUtils.isBlank(proString)){
			return JacksonUtil.fromJson(proString, SyResource.class);
		}
		return null;
	}
	
	@Override
	public List<SyMenu> getMenuList(){
		List<SyMenu> menuList = Lists.newArrayList();
		Jedis jedis = JedisUtil.getJedisClient();
		List<String> proList = jedis.hvals(AuthConstants.KEYS.MENU_KEY);
		JedisUtil.close(jedis);
		if (RdfsUtils.isEmpty(proList)) {
			log.error(RdfsUtils.merge("没有获取到菜单列表"));
		}else {
			for (String menu : proList) {
				if(!StringUtils.isBlank(menu)){
					menuList.add((SyMenu)JacksonUtil.fromJson(menu, SyMenu.class));
				}
			}
		}
		return menuList;
	}
	
	@Override
	public List<SyResource> getResourceList(){
		List<SyResource> resourceList = Lists.newArrayList();
		Jedis jedis = JedisUtil.getJedisClient();
		List<String> proList = jedis.hvals(AuthConstants.KEYS.RESOURCE_KEY);
		JedisUtil.close(jedis);
		if (RdfsUtils.isEmpty(proList)) {
			log.error(RdfsUtils.merge("没有获取到菜单列表"));
		}else {
			for (String menu : proList) {
				if(!StringUtils.isBlank(menu)){
					resourceList.add((SyResource)JacksonUtil.fromJson(menu, SyResource.class));
				}
			}
		}
		return resourceList;
	}
	
	@Override
	public List<SyPermSet> getPermsetList(){
		List<SyPermSet> resourceList = Lists.newArrayList();
		Jedis jedis = JedisUtil.getJedisClient();
		List<String> proList = jedis.hvals(AuthConstants.KEYS.PERMSET_KEY);
		JedisUtil.close(jedis);
		if (RdfsUtils.isEmpty(proList)) {
			log.error(RdfsUtils.merge("没有获取到菜单列表"));
		}else {
			for (String perm : proList) {
				if(!StringUtils.isBlank(perm)){
					resourceList.add((SyPermSet)JacksonUtil.fromJson(perm, SyPermSet.class));
				}
			}
		}
		return resourceList;
	}
	
	@Override
	public Map<String, SortedMap<Integer, List<TreeDto>>> getMenuTree(List<SyPermSet> permSets){
		Map<String, SortedMap<Integer, List<TreeDto>>> sortMap = new HashMap<>();
		if(permSets != null && !permSets.isEmpty()){
			for(SyPermSet permSet : permSets){
				List<SyResource> list = permSet.getItems();
				if(list!= null && !list.isEmpty()){
					for(SyResource resource : list){
						if(resource.getStatus().equals(Constants.IS.YES)){
							SyMenu menu = resource.getMenu();
							String pId = "0";
							if(menu!=null){
								pId = menu.getMenuId() + "";
							}
							TreeDto treeDto = new TreeDto(resource.getItemId()+"", pId,
									resource.getItemTitle(), resource.getItemLocation(), resource.getItemIcon(), false);
							if(sortMap.get(pId) == null){
								sortMap.put(pId, new TreeMap<>(new Comparator<Integer>() {
									@Override
									public int compare(Integer o1, Integer o2) {
										if (o1 != null && o2 != null) {
											return o1 - o2;
										}
										if (o1 == null && o2 != null) {
											return -1;
										}
										if (o1 != null && o2 == null) {
											return 1;
										}
										return 0;
									}
								}));
							}
							if(sortMap.get(pId).get(resource.getSortNo()) == null){
								sortMap.get(pId).put(resource.getSortNo(), new ArrayList<>());
							}
							if(!sortMap.get(pId).get(resource.getSortNo()).contains(treeDto)){
								sortMap.get(pId).get(resource.getSortNo()).add(treeDto);
							}
							if(!"0".equals(pId)){
								cascade(sortMap, pId);
							}
						}
					}
				}
			}
		}
		return sortMap;
	}
	
	public void cascade(Map<String, SortedMap<Integer, List<TreeDto>>> sortMap, String id){
		SyMenu menu = getMenu(id);
		String pId = "0";
		if(menu.getParMenu()!=null && menu.getParMenu().getMenuId()!=null){
			pId = menu.getParMenu().getMenuId() + "";
		}
		TreeDto treeDto = new TreeDto(menu.getMenuId()+"", pId,
				menu.getMenuTitle(), "", menu.getMenuIcon(), true);
		if(sortMap.get(pId) == null){
			sortMap.put(pId, new TreeMap<>(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					if (o1 != null && o2 != null) {
						return o1 - o2;
					}
					if (o1 == null && o2 != null) {
						return -1;
					}
					if (o1 != null && o2 == null) {
						return 1;
					}
					return 0;
				}
			}));
		}
		if(sortMap.get(pId).get(menu.getSortNo()) == null){
			sortMap.get(pId).put(menu.getSortNo(), new ArrayList<>());
		}
		if(!sortMap.get(pId).get(menu.getSortNo()).contains(treeDto)){
			sortMap.get(pId).get(menu.getSortNo()).add(treeDto);
		}

		if(!"0".equals(pId)){
			cascade(sortMap, pId);
		}
	}

	@Override
	public void cacheResource(SyResource resource) {
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(AuthConstants.KEYS.RESOURCE_KEY, resource.getItemId()+"", JacksonUtil.toJson(resource));
		JedisUtil.close(jedis);
	}

	@Override
	public void cacheMenu(SyMenu menu) {
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(AuthConstants.KEYS.MENU_KEY, menu.getMenuId()+"", JacksonUtil.toJson(menu));
		JedisUtil.close(jedis);
	}

	@Override
	public void cachePermset(SyPermSet permSet) {
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(AuthConstants.KEYS.PERMSET_KEY, permSet.getId()+"", JacksonUtil.toJson(permSet));
		JedisUtil.close(jedis);
	}
}
