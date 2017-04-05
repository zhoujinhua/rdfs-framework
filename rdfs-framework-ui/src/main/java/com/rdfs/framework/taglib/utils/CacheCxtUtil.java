package com.rdfs.framework.taglib.utils;

import com.rdfs.framework.taglib.service.CacheDataService;

public class CacheCxtUtil {

	public static CacheDataService cacheDataService;

	public static CacheDataService getCacheDataService() {
		return cacheDataService;
	}

	public static void setCacheDataService(CacheDataService cacheDataService) {
		CacheCxtUtil.cacheDataService = cacheDataService;
	}

}
