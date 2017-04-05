package com.rdfs.framework.core.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdfs.framework.core.bean.UserDto;
import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.core.redis.JedisUtil;

import redis.clients.jedis.Jedis;


public class AuthUtil {

	private static final ThreadLocal<UserDto> localVisitor = new ThreadLocal<UserDto>();
	private static Logger log = LoggerFactory.getLogger(AuthUtil.class);

	/**
	 * 设置当前线程用户信息
	 * @param userDto
	 */
	public static synchronized void setCurrentUserDto(UserDto userDto){
		localVisitor.set(userDto);
	}

	/**
	 * 取当前登录用户
	 * @return
	 */
	public static UserDto getCurrentUserDto(){
		return localVisitor.get();
	}
	
	/**
	 * 获取JUID
	 * 
	 * @param request
	 * @return
	 */
	public static String getJuid(HttpServletRequest request){
		String juid = request.getHeader("juid");
		if (RdfsUtils.isEmpty(juid)) {
			juid = request.getParameter("juid");
		}
		return juid;
	}
	
	/**
	 * 获取UserModel对象
	 * 
	 * @return
	 */
	public static UserDto getUserDto(HttpServletRequest request){
		String juid = getJuid(request);
		UserDto userDto = getUserDto(juid);
		return userDto;
	}

	public static UserDto getUserDto(String juid) {
		UserDto UserDto = null;
		if (RdfsUtils.isNotEmpty(juid)) {
			String userJson = JedisUtil.getString(juid);
			UserDto = (UserDto) JsonUtil.fromJson(userJson, UserDto.class);
			if (RdfsUtils.isEmpty(UserDto)) {
				log.debug(RdfsUtils.merge("没获取到JUID为[{0}]的UserDto用户对象", juid));
			}
		}
		return UserDto;
	}
	
	/**
	 * 用户心跳维持
	 * 
	 * @param juid
	 */
	public static void heartbeat(String juid){
		String value = getParam("user_login_timeout_");
		if(StringUtils.isBlank(value)){
			value = "18000";
		}
		JedisUtil.exprString(juid, Integer.valueOf(value));
	}
	
	/**
	 * 从缓存中获取全局参数配置值
	 * @param key
	 * @return
	 */
	public static String getParam(String key){
		String value = StringUtils.EMPTY;
		if (RdfsUtils.isEmpty(key)) {
			log.error("获取参数失败：全局参数配置Key不能为空。");
			return value;
		}
		Jedis jedis = JedisUtil.getJedisClient();
		value = jedis.hget(Constants.KEYS.PARAM_KEY, key);
		JedisUtil.close(jedis);
		return value;
	}
	
	/**
	 * 比较用户信息
	 * @param request
	 * @return
	 */
	public static boolean compareUserDto(HttpServletRequest request){
		UserDto userDto = getUserDto(request);
		if(userDto!=null){
			String clientIp = getClientIpAddr(request);
			String clientKey = request.getHeader("USER-AGENT");
			if(!StringUtils.isBlank(userDto.getClientIp()) && !StringUtils.isBlank(clientIp) && userDto.getClientIp().equals(clientIp)){
				if(!StringUtils.isBlank(userDto.getClientKey()) && !StringUtils.isBlank(clientKey) && userDto.getClientKey().equals(clientKey)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-real-ip");
		if (RdfsUtils.isEmpty(ip)) {
			ip = request.getHeader("X-Forward-For");
		}
		if (RdfsUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (RdfsUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (RdfsUtils.isEmpty(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtils.indexOf(ip, "0:0") != -1) {
			ip = "127.0.0.1";
		}
		return ip;
	}
}
