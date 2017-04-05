package com.rdfs.framework.hibernate.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.SessionImpl;
import org.hibernate.metadata.ClassMetadata;

import com.rdfs.framework.hibernate.enums.UpdateMode;

/**
 * 对hibernate session的封装
 * 干掉DAO层要实现对page对象的额封装
 */
public class CrapSession {
	private Session session;
	
	public CrapSession(Session session){
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> List<T> findEntitys(Class<T> type, Serializable...pks) {
		List<T> list = new ArrayList<T>();
		for(Serializable pk : pks){
			T result = (T) this.session.get(type, pk);
			list.add(result);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T find(Class<T> type, Serializable pk) {
		T result = (T) this.session.get(type, pk);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T find(Class<T> type, Serializable pk,LockOptions option) {
		return (T) this.session.get(type, pk,option);
	}
	
	public <C> CrapCriteria<C> getCriteria(Class<C> type) {
		//确保session是正常的
		if(!this.session.isConnected()){
			throw new SessionException( "Session is closed!" );
		}
		return new CrapCriteria<C>(type.getName(),(SessionImpl)this.session);
	}

	@SuppressWarnings("rawtypes")
	public CrapQuery createQuery(String ql) {
		return new CrapQuery(this.session.createQuery(ql),this.session);
	}

	public <T> CrapQuery<T> createQuery(Class<T> type, String ql) {
		return new CrapQuery<T>(this.session.createQuery(ql),this.session);
	}

	@SuppressWarnings("rawtypes")
	public CrapQuery createNativeQuery(String ql) {
		return new CrapQuery(this.session.createSQLQuery(ql),this.session);
	}

	public <T> CrapQuery<T> createNativeQuery(Class<T> type, String ql) {
		return new CrapQuery<T>(this.session.createSQLQuery(ql),this.session);
	}

	/**
	 * 合并实体，如果实体不存在，则新增
	 * @param entity
	 * @return
	 */
	public <T extends Serializable> T merge(T entity) {
		this.session.saveOrUpdate(entity);
		return entity;
	}
	
	/**
	 * 批量合并
	 * @param entities
	 * @return
	 */
	public <T extends Serializable> T[] merge(T ... entities) {
		for(T entity:entities){
			this.session.saveOrUpdate(entity);
		}
		return entities;
	}
	
	/**
	 * 批量合并
	 * @param entities
	 * @return
	 */
	public <T extends Serializable> Collection<T> merge(Collection<T> entities) {
		for(T entity:entities){
			this.session.saveOrUpdate(entity);
		}
		return entities;
	}
	/**
	 * 更新实体
	 * @param entity
	 * @return
	 */
	public <T extends Serializable> T update(T entity){
		this.session.update(entity);
		return entity;
	}
	
	/**
	 * 批量更新
	 * @param entities
	 * @return
	 */
	public <T extends Serializable> T[] update(T [] entities){
		for(T entity:entities){
			this.session.update(entity);
		}
		return entities;
	}
	
	/**
	 * 批量更新部分字段
	 * @param entities
	 * @param properties
	 * @return
	 */
	public <T extends Serializable> T[] update(T [] entities,String ... properties){
		return update(entities,UpdateMode.INCLUDE,properties);
	}
	
	/**
	 * 批量更新部分字段
	 * @param entities
	 * @param mode
	 * @param properties
	 * @return
	 */
	public <T extends Serializable> T[] update(T [] entities,UpdateMode mode,String ... properties){
		for(T entity:entities){
			this.update(entity,mode,properties);
		}
		return entities;
	}
	
	/**
	 * 批量更新
	 * @param entities
	 * @return
	 */
	public <T extends Serializable> Collection<T> update(Collection<T> entities){
		for(T entity:entities){
			this.session.update(entity);
		}
		return entities;
	}
	
	/**
	 * 批量更新部分字段
	 * @param entities
	 * @param properties
	 * @return
	 */
	public <T extends Serializable> Collection<T> update(Collection<T> entities,String ... properties){
		return update(entities,UpdateMode.INCLUDE,properties);
	}
	
	/**
	 * 批量更新部分字段
	 * @param entities
	 * @param mode
	 * @param properties
	 * @return
	 */
	public <T extends Serializable> Collection<T> update(Collection<T> entities,UpdateMode mode,String ... properties){
		for(T entity:entities){
			this.update(entity,mode,properties);
		}
		return entities;
	}
	
	/**
	 * 更新实体中的部分属性
	 * @param entity
	 * @param properties 需要更新的属性列表
	 * @return
	 */
	public <T extends Serializable> T update(T entity,String ... properties){
		return update(entity,UpdateMode.INCLUDE,properties);
	}
	
	/**
	 * 更新实体的部分属性
	 * @param entity
	 * @param mode 更新模式，当取值为{@link UpdateMode#INCLUDE}时，只更新properties所指定的字段，当取值为{@link UpdateMode#EXCLUDE}时，不更新properties所指定的字段
	 * @param properties 要更新或排除的字段列表
	 * @return
	 */
	public <T extends Serializable> T update(T entity,UpdateMode mode,String ... properties){
		SessionFactory factory = this.session.getSessionFactory();
		ClassMetadata meta = factory.getClassMetadata(entity.getClass());
		Serializable pk = meta.getIdentifier(entity, (SessionImpl)this.session);
		if(pk.equals(entity)){
			try {
				pk = entity.getClass().newInstance();
				PropertyUtils.copyProperties(pk, entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		@SuppressWarnings("unchecked")
		T old = (T) this.find(entity.getClass(), pk);
		if(UpdateMode.EXCLUDE.equals(mode)){
			for(String name : properties){
				try {
					PropertyUtils.setProperty(entity, name, PropertyUtils.getProperty(old, name));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			this.session.merge(entity);
			return entity;
		}else{
			for(String name : properties){
				try {
					PropertyUtils.setProperty(old, name, PropertyUtils.getProperty(entity, name));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			this.session.update(old);
			return old;
		}
	}
	
	/**
	 * 持久化对象
	 * @param entity
	 * @return
	 */
	public <T extends Serializable> T persist(T entity) {
		this.session.save("Computer",entity);
		return entity;
	}
	
	/**
	 * 批量持久化
	 * @param entity
	 * @return
	 */
	public <T extends Serializable> T[] persist(T ... entities){
		for(T entity:entities){
			this.persist(entity);
		}
		return entities;
	}
	
	/**
	 * 批量持久化
	 * @param entities
	 * @return
	 */
	public <T extends Serializable> Collection<T> persist(Collection<T> entities){
		for(T entity:entities){
			this.persist(entity);
		}
		return entities;
	}

	/**
	 * 移除持久对象
	 * @param entity
	 */
	public <T extends Serializable> T remove(T entity) {
		this.session.delete(entity);
		return entity;
	}
	
	/**
	 * 批量移除持久对象
	 * @param entities
	 */
	public void remove(Serializable ... entities){
		for(Serializable entity:entities){
			this.remove(entity);
		}
	}
	
	/**
	 * 批量移除持久对象
	 * @param entities
	 */
	public <T extends Serializable>void remove(Collection<T> entities){
		for(Serializable entity:entities){
			this.remove(entity);
		}
	}
	
	/**
	 * 移除持久对象
	 * @param clazz
	 * @param pk
	 */
	public <T extends Serializable> T remove(Class<T> clazz,Serializable pk){
		T object = this.find(clazz, pk);
		this.remove(object);
		return object;
	}
	
	/**
	 * 将对象从持久态切换到游离态
	 * @param obj
	 */
	public void evict(Serializable obj){
		this.session.evict(obj);
	}
	
	/**
	 * 移除持久对象
	 * @param clazz
	 * @param pk
	 * @return
	 */
	public <T extends Serializable> void remove(Class<T> clazz,Serializable ... pks){
		List<T> datas = this.list(clazz, pks);
		if(datas==null){
			return;
		}
		for(T obj:datas){
			this.session.delete(obj);
		}
	}
	
	/**
	 * 根据给定的主键列表，查询相应的持久对象列表
	 * @param clazz
	 * @param pks
	 * @return
	 */
	public <T extends Serializable> List<T> list(Class<T> clazz,Serializable ... pks) {
		if(pks==null || pks.length == 0){
			return new ArrayList<T>(0);
		}
		//取得主键的属性名称
		String pkName = this.session.getSessionFactory().getClassMetadata(clazz).getIdentifierPropertyName();
		//通过in查询
		return this.getCriteria(clazz).add(Restrictions.in(pkName, pks)).getResultList();
	}
	
	/**
	 * 查询持久对象列表
	 * @param clazz
	 * @return
	 */
	public <T extends Serializable> List<T> list(Class<T> clazz) {
		return this.getCriteria(clazz).getResultList();
	}
	
	/**
	 * 根据参数获取对象列表
	 * @param clazz
	 * @param map
	 * @return
	 */
	public <T extends Serializable> List<T> list(Class<T> clazz,Map<String,Object> map){
		CrapCriteria<T> cri = this.getCriteria(clazz);
		if(map!=null && !map.isEmpty()){
			for(Iterator<String> iter = map.keySet().iterator();iter.hasNext();){
				String param = iter.next();
				cri.add(Restrictions.eq(param, map.get(param)));
			}
		}
		return cri.getResultList();
	}
	
}
