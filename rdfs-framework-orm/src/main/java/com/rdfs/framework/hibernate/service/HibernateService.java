package com.rdfs.framework.hibernate.service;

import java.io.Serializable;
import java.util.List;

import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.enums.OperMode;
import com.rdfs.framework.hibernate.enums.OrderMode;
import com.rdfs.framework.hibernate.enums.UpdateMode;


public interface HibernateService {

	/**
	 * 分页
	 * @param t 分页依据对象
	 * @param pn 查询页码
	 * @param orderMode 排序类型
	 * @param orderParam 排序字段
	 * @param operMode 查询类型
	 * @param params 查询字段
	 * @return
	 */
	<T extends Serializable> Page pageList(T t, Page page, OrderMode orderMode, String orderParam, OperMode operMode, String...params);

	/**
	 * 分页查询
	 * @param t 封装的存值对象
	 * @param pn 查询页面
	 * @param operMode 查询类型 eq|like
	 * @param params 查询参数
	 * @return
	 */
	<T extends Serializable> Page pageList(T t, Page page, OperMode operMode, String...params);
	
	/**
	 * 按HQL分页对象
	 * @param pn 查询页码
	 * @param hql hql语句
	 * @return
	 */
	<T extends Serializable> Page pageList(Page page, String hql);
	
	/**
	 * 按sql分页
	 * @param pn 查询页码
	 * @param sql 本地sql语句
	 * @return
	 */
	List<?> pageSqlList(Page page, String sql);
	
	/**
	 * 根据Hql查询对象
	 * @param hql hql语句
	 * @return
	 */
	<T extends Serializable> List<T> getList(String hql);
	
	/**
	 * 根据储值对象获取对象列表
	 * @param t 储值查询对象
	 * @param orderMode 排序方式
	 * @param field 排序字段
	 * @param operMode eq|like
	 * @param params 查询字段
	 * @return
	 */
	<T extends Serializable> List<T> getList(T t, OrderMode orderMode, String field, OperMode operMode, String...params);

	/**
	 * 根据储值对象获取对象列表|不排序
	 * @param t 储值查询对象
	 * @param operMode eq|like
	 * @param params 查询参数
	 * @return
	 */
	<T extends Serializable> List<T> getList(T t, OperMode operMode, String... params);

	/**
	 * 根据储值对象获取对象列表 |不排序，默认eq
	 * @param t 储值查询对象
	 * @param params 查询参数
	 * @return
	 */
	<T extends Serializable> List<T> getList(T t, String...params);
	
	/**
	 * 根据sql查询对象数组
	 * @param sql 本地sql脚本
	 * @param point 查询起始值
	 * @param step 查询步长
	 * @return
	 */
	List<?> getSqlList(String sql, Integer point, Integer step);
	
	/**
	 * 根据sql查询对象数组
	 * @param sql 本地sql脚本
	 * @return
	 */
	List<?> getSqlList(String sql);
	
	/**
	 * 保存对象
	 * @param t
	 */
	<T extends Serializable> T saveEntity(T t);
	
	/**
	 * 更新整个对象
	 * @param t
	 * @return
	 */
	<T extends Serializable> T updateEntity(T t);
	
	/**
	 * 按字段更新对象
	 * mode 排除/包含
	 * @param t
	 * @param params
	 */
	<T extends Serializable> T updateEntity(T t, UpdateMode mode, String...params);
	
	/**
	 * 按字段更新对象
	 * @param t
	 * @param params
	 */
	<T extends Serializable> T updateEntity(T t, String...params);
	
	/**
	 * 更新保存对象
	 * @param t
	 * @return
	 */
	<T extends Serializable> T mergeEntity(T t);
	/**
	 * 删除对象
	 * @param t
	 */
	<T extends Serializable> void deleteEntity(T t);
	
	/**
	 * 根据主键ID查询对象
	 * @param type 指定bean
	 * @param id 对象ID
	 * @param init 是否加载非懒加载对象
	 * @return
	 */
	<T extends Serializable> T getEntityById(Class<T> type, int id, boolean init); 

	/**
	 * 根据主键ID查询对象
	 * @param type 指定bean
	 * @param id 对象ID
	 * @param init 是否加载非懒加载对象
	 * @return
	 */
	<T extends Serializable> T getEntityByCode(Class<T> type, String id, boolean init);




}
