package com.rdfs.framework.core.utils;

import java.util.UUID;

import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.core.exeption.RdfsException;
import com.rdfs.framework.core.redis.JedisUtil;

import redis.clients.jedis.Jedis;

/**
 */
public class UUIDUtil {
	
	/**
	 * 返回去除连接符-的UUID
	 * 
	 * @return
	 */
	public static String uuid() {
		String uuid = rawUuid();
		return uuid.replaceAll("-", "");
	}
	
	/**
	 * 返回原生UUID
	 * 
	 * @return
	 */
	public static String rawUuid() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * <p>获取规则序列号：基于Redis生成</p>
	 * 生成规则：[前缀] + 时间戳 + 递增重复循环的序列
	 * 
	 * @param idType ID类型 用于作为Redis Key的一部分。标识ID的唯一性。
	 * @param timeFormat 时间戳的格式 缺省值：yyMMddHHmmss
	 * @param maxIncr 循环递增序列最大值 9999
	 * @return
	 */
	public static String appId(String idType, String timeFormat, String maxIncr){
		if (RdfsUtils.isEmpty(idType)) {
			throw new RdfsException("idType不能为空，请分配ID类型标识。");
		}
		Jedis jedis = JedisUtil.getJedisClient();
		String id = null;
		String IDSET = "IDSET";
		String key = Constants.KEYS.ID + idType;
		timeFormat = RdfsUtils.isEmpty(timeFormat) ? "yyMMddHHmmss" : timeFormat;
		maxIncr = RdfsUtils.isEmpty(maxIncr) ? "9999" : maxIncr;
		while (true) {
			long myIncrLong = jedis.incr(key);
			if (myIncrLong > Integer.valueOf(maxIncr)) {
				jedis.del(key);
				myIncrLong = jedis.incr(key);
			}
			String myIncrStr = String.format("%" + maxIncr.length() + "d", myIncrLong).replace(" ", "0");
			String myPrefix = RdfsUtils.getDateStr(timeFormat);
			id = myPrefix + myIncrStr;
			if (!jedis.sismember(IDSET, id)) {
				jedis.sadd(IDSET, id);
				break;
			}
		}
		JedisUtil.close(jedis);
		return id;
	}
	
	/**
	 * * <p>获取数值型规则序列号</p>
	 * 
	 * @param idType
	 * @return
	 */
	public static String appId(String idType){
		return appId(idType, null, null);
	}

}
