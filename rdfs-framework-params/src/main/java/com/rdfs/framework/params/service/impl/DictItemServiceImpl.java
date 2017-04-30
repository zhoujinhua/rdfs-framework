package com.rdfs.framework.params.service.impl;

import org.springframework.stereotype.Service;

import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.params.entity.SyDictItem;
import com.rdfs.framework.params.service.DictItemService;

@Service
public class DictItemServiceImpl extends HibernateServiceSupport implements DictItemService {

	@Override
	public SyDictItem saveDictItem(SyDictItem dict) {
		if(dict.getId()==null){
			saveEntity(dict);
		} else {
			getSession().update(dict);
		}
		return dict;
	}

}
