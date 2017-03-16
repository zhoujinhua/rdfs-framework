package com.rdfs.framework.cache.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rdfs.framework.cache.service.CacheParamsService;
import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.core.redis.JedisUtil;
import com.rdfs.framework.core.utils.AuthUtil;
import com.rdfs.framework.core.utils.RdfsUtils;
import com.rdfs.framework.hibernate.enums.OperMode;
import com.rdfs.framework.hibernate.enums.OrderMode;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.params.entity.SyDictItem;
import com.rdfs.framework.params.entity.SyParameter;
import com.rdfs.framework.params.entity.SyRegion;
import com.rdfs.framework.taglib.bean.DictItem;
import com.rdfs.framework.taglib.bean.Region;
import com.rdfs.framework.taglib.utils.JacksonUtil;

import redis.clients.jedis.Jedis;

public class CacheParamsServiceImpl extends HibernateServiceSupport implements CacheParamsService {

private static Logger log = LoggerFactory.getLogger(CacheParamsServiceImpl.class);
	
	/**
	 * 将全局参数信息刷到缓存
	 */
	@Override
	public void cacheParamData() {
		List<SyParameter> parameters = getList(" from SyParameter");
		Map<String, String> cacheMap = Maps.newHashMap();
		for (SyParameter parameter : parameters) {
			cacheMap.put(parameter.getKey(), parameter.getValue());
		}
		if (RdfsUtils.isNotEmpty(cacheMap)) {
			Jedis jedis = JedisUtil.getJedisClient();
			jedis.hmset(Constants.KEYS.PARAM_KEY, cacheMap);
			JedisUtil.close(jedis);
		}
	}
	
	/**
	 * 将单个参数配置项刷到缓存，如果已存在则覆盖
	 * @param fieldKey 参数KEY
	 * @param value 参数值
	 */
	@Override
	public void cacheParamOption(String fieldKey, String value) {
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.PARAM_KEY, fieldKey, value);
		JedisUtil.close(jedis);
	}
	
	/**
	 * 将单个参数配置项刷从缓存中删除
	 * @param fieldKey 参数KEY
	 */
	public void delParamOption(String fieldKey) {
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hdel(Constants.KEYS.PARAM_KEY, fieldKey);
		JedisUtil.close(jedis);
	}

	/**
	 * 从缓存中获取全局参数配置值
	 * 
	 * @param key
	 * @return
	 */
	public String getParam(String key) {
		return AuthUtil.getParam(key);
	}

	/**
	 * 将字典表刷到缓存
	 */
	@Override
	public void cacheDictData() {
		SyDictItem item = new SyDictItem();
		item.setStatus(Constants.IS.YES);
		List<SyDictItem> dictItems = getList(item, OrderMode.DESC, "sortNo", OperMode.EQ, "isEnable");
		Jedis jedis = JedisUtil.getJedisClient();
		// 将字典对照项目载入缓存
		for (SyDictItem dictItem : dictItems) {
			jedis.hset(Constants.KEYS.DIC_KEY + dictItem.getKey(), dictItem.getCode(), JacksonUtil.toJson(dictItem));
		}
		JedisUtil.close(jedis);
	}
	
	/**
	 * 将单个字典刷到缓存
	 */
	@Override
	public void cacheDict(SyDictItem dictItem) {
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hset(Constants.KEYS.DIC_KEY + dictItem.getKey(), dictItem.getCode(), JacksonUtil.toJson(dictItem));
		JedisUtil.close(jedis);
	}
	
	/**
	 * 将单个字典从缓存中删除
	 */
	public void delDic(SyDictItem dictItem) {
		Jedis jedis = JedisUtil.getJedisClient();
		jedis.hdel(Constants.KEYS.DIC_KEY + dictItem.getKey(), dictItem.getCode());
		JedisUtil.close(jedis);
	}

	/**
	 * 从缓存中获取字典对照集合
	 * 
	 * @param key
	 * @return
	 */
	public List<DictItem> getDicList(String key) {
		List<DictItem> dictItems = Lists.newArrayList();
		if (RdfsUtils.isEmpty(key)) {
			log.error("获取字典对照失败：字典Key不能为空。");
			return dictItems;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		List<String> dicList = jedis.hvals(Constants.KEYS.DIC_KEY + key);
		JedisUtil.close(jedis);
		if (RdfsUtils.isEmpty(dicList)) {
			log.error(RdfsUtils.merge("没有获取到Key为[{0}]的数据字典。", key));
		}else {
			for (String dicString : dicList) {
				dictItems.add((DictItem)JacksonUtil.fromJson(dicString, SyDictItem.class));
			}
		}
		return dictItems;
	}

	/**
	 * 从缓存中获取字典对照描述
	 * 
	 * @param key
	 * @param code
	 * @return
	 */
	public String getDicDesc(String key, String code) {
		String desc = StringUtils.EMPTY;
		if (RdfsUtils.isEmpty(key)) {
			log.error("获取字典对照失败：字典Key不能为空。");
			return desc;
		}
		if (RdfsUtils.isEmpty(code)) {
			log.error("获取字典对照失败：字典code不能为空。");
			return desc;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String dicJson = jedis.hget(Constants.KEYS.DIC_KEY + key, code);
		JedisUtil.close(jedis);
		if (RdfsUtils.isNotEmpty(dicJson)) {
			SyDictItem aos_dicPO = (SyDictItem)JacksonUtil.fromJson(dicJson, SyDictItem.class);
		    desc = aos_dicPO.getDesc();
		}
		return desc;
	}

	@Override
	public void cacheRegionData(){
		Map<String, String> map = new HashMap<>();
		List<SyRegion> provinces = getList(" from SyRegion where regLevel = '0'");
		Jedis jedis = JedisUtil.getJedisClient();
		for(SyRegion province : provinces){
			jedis.hset(Constants.KEYS.REGION_KEY, province.getRegCode(), JacksonUtil.toJson(province));
			jedis.hset(Constants.KEYS.REGION_PROVINCE_KEY, province.getRegCode(), JacksonUtil.toJson(province));
			SyRegion syRegion = new SyRegion();
			syRegion.setRegCodePar(province.getRegCode());
			List<SyRegion> citys = getList(syRegion, "regCodePar");
			
			if(citys!=null && !citys.isEmpty()){
				map.put(province.getRegCode(), JacksonUtil.toJson(citys));
				for(SyRegion city : citys){
					jedis.hset(Constants.KEYS.REGION_KEY, city.getRegCode(), JacksonUtil.toJson(city));
				}
			}
		}
		jedis.hmset(Constants.KEYS.REGION_CITY_KEY, map);
		JedisUtil.close(jedis);
	}
	
	@Override
	public List<Region> getProvinceList(){
		List<Region> regions = Lists.newArrayList();
		Jedis jedis = JedisUtil.getJedisClient();
		List<String> proList = jedis.hvals(Constants.KEYS.REGION_PROVINCE_KEY);
		JedisUtil.close(jedis);
		if (RdfsUtils.isEmpty(proList)) {
			log.error(RdfsUtils.merge("没有获取到省份列表"));
		}else {
			for (String province : proList) {
				regions.add((Region)JacksonUtil.fromJson(province, SyRegion.class));
			}
		}
		return regions;
	}
	
	@Override
	public List<Region> getCityList(String regCode){
		List<Region> regions = Lists.newArrayList();
		if (RdfsUtils.isEmpty(regCode)) {
			log.error("获取城市失败：省份代码不能为空。");
			return regions;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String jsonValue = jedis.hget(Constants.KEYS.REGION_CITY_KEY, regCode);
		JedisUtil.close(jedis);
		if(!StringUtils.isBlank(jsonValue)){
			regions = JacksonUtil.fromJson(jsonValue, new TypeReference<List<Region>>(){});
		}
		return regions;
	}
	
	@Override
	public SyRegion getRegion(String regCode){
		if (RdfsUtils.isEmpty(regCode)) {
			log.error("获取地区信息失败：地区代码不能为空。");
			return null;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String proString = jedis.hget(Constants.KEYS.REGION_KEY, regCode);
		JedisUtil.close(jedis);
		return JacksonUtil.fromJson(proString, SyRegion.class);
	}
	
	@Override
	public List<Region> getRegionList(){
		List<Region> regions = Lists.newArrayList();
		Jedis jedis = JedisUtil.getJedisClient();
		List<String> proList = jedis.hvals(Constants.KEYS.REGION_KEY);
		JedisUtil.close(jedis);
		if (RdfsUtils.isEmpty(proList)) {
			log.error(RdfsUtils.merge("没有获取到区域列表"));
		}else {
			for (String province : proList) {
				regions.add((Region)JacksonUtil.fromJson(province, SyRegion.class));
			}
		}
		return regions;
	}
}
