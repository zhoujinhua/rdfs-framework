package com.rdfs.framework.params.service;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.params.entity.SyDictItem;

public interface DictItemService extends HibernateService {

	SyDictItem saveDictItem(SyDictItem arg);

}
