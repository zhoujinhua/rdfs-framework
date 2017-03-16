package com.rdfs.framework.cache.service;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.params.entity.SyDictItem;
import com.rdfs.framework.taglib.service.CacheDataService;

public interface CacheParamsService extends HibernateService, CacheDataService{

	void cacheDict(SyDictItem dictItem);

	void cacheParamOption(String fieldKey, String value);

	void cacheRegionData();

	void cacheParamData();

	void cacheDictData();

}
