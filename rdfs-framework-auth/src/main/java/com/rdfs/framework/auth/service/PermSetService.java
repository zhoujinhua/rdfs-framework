package com.rdfs.framework.auth.service;

import java.util.List;

import com.rdfs.framework.auth.entity.SyPermSet;
import com.rdfs.framework.core.bean.TreeDto;
import com.rdfs.framework.core.bean.UserDto;
import com.rdfs.framework.hibernate.service.HibernateService;

public interface PermSetService extends HibernateService {

	/*List<TreeDto> getPermSetTree(SyUser user) throws Exception;*/

	void updatePermSet(SyPermSet permSet, String[] split);
	
	List<TreeDto> getMenuTree(SyPermSet permSet) throws Exception;

	void savePermSet(SyPermSet permSet, UserDto user);
}
