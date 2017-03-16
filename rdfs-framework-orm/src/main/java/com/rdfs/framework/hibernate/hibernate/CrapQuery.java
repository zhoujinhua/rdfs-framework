package com.rdfs.framework.hibernate.hibernate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NonUniqueResultException;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;

import com.rdfs.framework.hibernate.bean.Page;



/**
 * @param <Q>
 */
public class CrapQuery<Q> {
	private org.hibernate.Query query;
	private Map<String,Object> queryParamMap;
	private Session session;
	
	public CrapQuery(org.hibernate.Query query,Session session){
		this.query = query;
		this.session = session;
	}

	public CrapQuery<Q> setMaxResult(int cnt) {
		query.setMaxResults(cnt);
		return this;
	}
	
	public CrapQuery<Q> setFirstResult(int cnt) {
		query.setFirstResult(cnt);
		return this;
	}
	
	public List<Q> getResultList() {
		return getResultList(false);
	}
	
	@SuppressWarnings("unchecked")
	public List<Q> getResultList(boolean cacheable) {
		this.setCacheable(cacheable);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Class<T> type) {
		return (List<T>)getResultList();
	}
	
	/**
	 * 取得查询的结果集
	 * @param type
	 * @param cacheable 是否使用缓存
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Class<T> type,boolean cacheable) {
		return (List<T>)getResultList(cacheable);
	}
	
	/**
	 * 获取查询结果集
	 * @param page
	 * @return
	 */
	public Page getResultList(Page page) {
		return getResultList(page,false);
	}
	
	/**
	 * 获取查询结果集
	 * @param page
	 * @param cacheable 是否使用缓存
	 * @return
	 */
	public Page getResultList(Page page,boolean cacheable) {
		initPage(page,cacheable);
		this.setFirstResult(page.getStart());
		this.setMaxResult(page.getLimit());
		page.setItems(getResultList(cacheable));
		return page;
	}
	/**
	 * 初始化Page对象，统计出记录总数
	 * @param page
	 * @param cacheable 是否使用缓存 
	 * @return
	 */
	private Page initPage(Page page,boolean cacheable){
		Query cntQuery;
		String ql = query.getQueryString();
		if(query instanceof SQLQuery){
			String sql = "select count(*) from (" + ql +") cntQuery";
			cntQuery = session.createSQLQuery(sql);
		}else{
			String hql = "select count(*) "+ql.substring(ql.indexOf("from")).replaceAll("[fF][eE][tT][Cc][Hh]", "");
			
			String tmpHql = hql.replaceAll("\n", " ").replaceAll("\r", " ");
			int idx = tmpHql.indexOf(" order by ");
			if(idx!=-1)
				hql = hql.substring(0, idx);
			
			//HQL
			cntQuery = session.createQuery(hql);
		}
		
		//将参数复制到统计总量的Query中
		if(queryParamMap != null){
			for(Entry<String,Object> entry : queryParamMap.entrySet()){
				cntQuery.setParameter(entry.getKey(), entry.getValue());
			}
		}
		
		cntQuery.setCacheable(cacheable);
		
		Number cnt = (Number) cntQuery.uniqueResult();
		if(cnt!=null)
			page.setCount(cnt.intValue());
		
		return page;
	}
	
	/**
	 * 设置结果转换器
	 * @param transformer
	 * @return
	 */
	public CrapQuery<Q> setResultTransfer(ResultTransformer transformer){
		this.query.setResultTransformer(transformer);
		return this;
	}
	
	public CrapQuery<Q> setParameter(String name, Object value) {
		//记录传入的参数
		if(queryParamMap == null){
			queryParamMap = new HashMap<String,Object>();
		}
		queryParamMap.put(name, value);
		query.setParameter(name, value);
		return this;
	}

	public <T> T getSingleResult(Class<T> type) {
		List<T> result = getResultList(type);
		if(result == null || result.size()>0){
			return null;
		}
		if(result.size()>1){
			throw new NonUniqueResultException();
		}
		return result.get(0);
	}
	
	/**
	 * 取得结果集的第一个对象
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getFirstResult(Class<T> type){
		return (T)this.getFirstResult();
	}
	
	/**
	 * 取得第一个结果集
	 * @param type
	 * @param cacheable
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getFirstResult(Class<T> type,boolean cacheable){
		return (T)this.getFirstResult(cacheable);
	}
	
	/**
	 * 获取第一个查询结果
	 * @return
	 */
	public Object getFirstResult(){
		return getFirstResult(false);
	}
	
	/**
	 * 获取第一个结果
	 * @param cacheable
	 * @return
	 */
	public Object getFirstResult(boolean cacheable){
		List<Q> result = getResultList(cacheable);
		if(result == null || result.isEmpty()){
			return null;
		}
		return result.get(0);
	}
	
	
	public Q getSingleResult() {
		List<Q> result = getResultList();
		
		return result!=null && result.size()>0?result.get(0):null;
	}
	
	public CrapQuery<Q> setCacheable(boolean cacheable){
		this.query.setCacheable(cacheable);
		return this;
	}

	public int executeUpdate() {
		return query.executeUpdate();
	}
}
