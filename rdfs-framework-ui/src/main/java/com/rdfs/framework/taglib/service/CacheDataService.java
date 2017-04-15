package com.rdfs.framework.taglib.service;

import java.util.List;

import com.rdfs.framework.taglib.bean.DictItem;
import com.rdfs.framework.taglib.bean.Region;

public interface CacheDataService {

	String getParam(String key);
	
	List<DictItem> getDicList(String key);

	String getDictJson(String key);

	List<Region> getCityList(String provinceCode);

	List<Region> getProvinceList();

	String getDicDesc(String key, String code);

	Region getRegion(String regCode);

	String getRegionJson();

}
