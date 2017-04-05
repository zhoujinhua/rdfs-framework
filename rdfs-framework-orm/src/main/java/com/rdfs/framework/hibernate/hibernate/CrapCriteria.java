package com.rdfs.framework.hibernate.hibernate;

import java.io.Serializable;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;

import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.enums.OperMode;
import com.rdfs.framework.hibernate.utils.BeanInvokeUtils;


/**
 * @param <C>
 */
public class CrapCriteria<C> implements Serializable{
	private static final long serialVersionUID = -7510240719997340848L;
	
	private Criteria query;
	private Criteria count;

	public CrapCriteria(String entityOrClassName,
			SessionImplementor session) {
		query = new CriteriaImpl(entityOrClassName,session); 
		count = new CriteriaImpl(entityOrClassName,session);
	}
	
	private CrapCriteria(Criteria query,Criteria count){
		this.query = query;
		this.count = count;
	}
	
	public CrapCriteria(String entityOrClassName,String alias,
			SessionImplementor session) {
		query = new CriteriaImpl(entityOrClassName,alias,session);
		count = new CriteriaImpl(entityOrClassName,alias,session);
	}

	/**
	 * 获取查询结果列表
	 * @return
	 */
	public List<C> getResultList(){
		return getResultList(false);
	}
	
	/**
	 * 获取查询结果列表
	 * @param cacheable 是否使用缓存
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<C> getResultList(boolean cacheable){
		this.setCacheable(cacheable);
		return query.list();
	}
	
	public Page getResultList(Page page,boolean cacheable){
		this.setCacheable(cacheable);
		initPage(page);
		this.setFirstResult(page.getStart());
		this.setMaxResults(page.getLimit());
		page.setItems(getResultList());
		return page;
	}
	
	/**
	 * 分页获取查询结果
	 * @param page
	 * @return
	 */
	public Page getResultList(Page page){
		return getResultList(page,false);
	}
	
	private Page initPage(Page page){
		//统计记录数
		Number countNum = 0;
		count.setProjection(Projections.rowCount());
		countNum = (Number) count.uniqueResult();
		
		if(countNum!=null)
			page.setCount(countNum.intValue());
		
		return page;
	}


	public String getAlias() {
		return query.getAlias();
	}

	public CrapCriteria<C> setProjection(Projection projection) {
		query.setProjection(projection);
		return this;
	}

	public CrapCriteria<C> add(Criterion criterion) {
		count.add(criterion);
		query.add(criterion);
		return this;
	}

	public CrapCriteria<C> addOrder(Order order) {
		query.addOrder(order);
		return this;
	}
	
	/**
	 * 设置结果转换器
	 * @param transformer
	 * @return
	 */
	public CrapCriteria<C> setResultTransfer(ResultTransformer transformer){
		this.query.setResultTransformer(transformer);
		return this;
	}

	public CrapCriteria<C> setFetchMode(String associationPath, FetchMode mode)
			throws HibernateException {
		count.setFetchMode(associationPath, mode);
		query.setFetchMode(associationPath, mode);
		return this;
	}

	public CrapCriteria<C> setLockMode(LockMode lockMode) {
		query.setLockMode(lockMode);
		return this;
	}

	public CrapCriteria<C> setLockMode(String alias, LockMode lockMode) {
		query.setLockMode(alias, lockMode);
		return this;
	}

	public CrapCriteria<C> createAlias(String associationPath, String alias)
			throws HibernateException {
		return new CrapCriteria<C>(query.createAlias(associationPath, alias),count.createAlias(associationPath, alias));
	}


	public CrapCriteria<C> createAlias(String associationPath, String alias,
			int joinType) throws HibernateException {
		Criteria count = this.count.createAlias(associationPath, alias,joinType);
		Criteria query = this.query.createAlias(associationPath,alias, joinType);
		return new CrapCriteria<C>(query,count);
	}


	public CrapCriteria<C> createAlias(String associationPath, String alias,
			int joinType, Criterion withClause) throws HibernateException {
		Criteria count = this.count.createAlias(associationPath, alias, joinType, withClause);
		Criteria query = this.query.createAlias(associationPath, alias, joinType, withClause);
		return new CrapCriteria<C>(query,count);
	}


	public CrapCriteria<C> createCriteria(String associationPath)
			throws HibernateException {
		Criteria count = this.count.createCriteria(associationPath);
		Criteria query = this.query.createCriteria(associationPath);
		return new CrapCriteria<C>(query,count);
	}


	public CrapCriteria<C> createCriteria(String associationPath, int joinType)
			throws HibernateException {
		Criteria count = this.count.createCriteria(associationPath,joinType);
		Criteria query = this.query.createCriteria(associationPath,joinType);
		return new CrapCriteria<C>(query,count);
	}


	public CrapCriteria<C> createCriteria(String associationPath, String alias)
			throws HibernateException {
		Criteria count = this.count.createCriteria(associationPath,alias);
		Criteria query = this.query.createCriteria(associationPath,alias);
		return new CrapCriteria<C>(query,count);
	}


	public CrapCriteria<C> createCriteria(String associationPath, String alias,
			int joinType) throws HibernateException {
		Criteria count = this.count.createCriteria(associationPath,alias,joinType);
		Criteria query = this.query.createCriteria(associationPath,alias,joinType);
		return new CrapCriteria<C>(query,count);
	}


	public CrapCriteria<C> createCriteria(String associationPath, String alias,
			int joinType, Criterion withClause) throws HibernateException {
		Criteria count = this.count.createCriteria(associationPath,alias,joinType,withClause);
		Criteria query = this.query.createCriteria(associationPath,alias,joinType,withClause);
		return new CrapCriteria<C>(query,count);
	}


	public CrapCriteria<C> setResultTransformer(ResultTransformer resultTransformer) {
		query.setResultTransformer(resultTransformer);
		return this;
	}


	public CrapCriteria<C> setMaxResults(int maxResults) {
		query.setMaxResults(maxResults);
		return this;
	}


	public CrapCriteria<C> setFirstResult(int firstResult) {
		query.setFirstResult(firstResult);
		return this;
	}

	/**
	 * 取第一个结果
	 * @param cacheable
	 * @return
	 */
	public C getFirstResult(boolean cacheable){
		List<C> result = getResultList(cacheable);
		if(result == null || result.isEmpty()){
			return null;
		}
		return result.get(0);
	}
	
	/**
	 * 获取第一个结果
	 * @return
	 */
	public C getFirstResult(){
		return getFirstResult(false);
	}
	
	/**
	 * 取单个结果，如果存在多条记录时则抛出异常
	 * @return
	 */
	public C getSingleResult() {
		return getSingleResult(false);
	}
	
	/**
	 * 取单个结果，如果存在多条记录时则抛出异常
	 * @param cacheable
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public C getSingleResult(boolean cacheable) {
		return (C) this.uniqueResult(cacheable);
	}
	
	
	public boolean isReadOnlyInitialized() {
		return query.isReadOnlyInitialized();
	}


	public boolean isReadOnly() {
		return query.isReadOnly();
	}


	public CrapCriteria<C> setReadOnly(boolean readOnly) {
		query.setReadOnly(readOnly);
		return this;
	}


	public CrapCriteria<C> setFetchSize(int fetchSize) {
		query.setFetchSize(fetchSize);
		return this;
	}


	public CrapCriteria<C> setTimeout(int timeout) {
		query.setTimeout(timeout);
		return this;
	}


	public CrapCriteria<C> setCacheable(boolean cacheable) {
		this.count.setCacheable(cacheable);
		query.setCacheable(cacheable);
		return this;
	}


	public CrapCriteria<C> setCacheRegion(String cacheRegion) {
		query.setCacheRegion(cacheRegion);
		return this;
	}


	public CrapCriteria<C> setComment(String comment) {
		query.setComment(comment);
		return this;
	}


	public CrapCriteria<C> setFlushMode(FlushMode flushMode) {
		query.setFlushMode(flushMode);
		return this;
	}


	public CrapCriteria<C> setCacheMode(CacheMode cacheMode) {
		query.setCacheMode(cacheMode);
		return this;
	}



	public ScrollableResults scroll() throws HibernateException {
		return query.scroll();
	}


	public ScrollableResults scroll(ScrollMode scrollMode)
			throws HibernateException {
		return query.scroll(scrollMode);
	}


	public Object uniqueResult() throws HibernateException {
		return this.uniqueResult(false);
	}
	
	public Object uniqueResult(boolean cacheable) throws HibernateException {
		query.setCacheable(cacheable);
		return query.uniqueResult();
	}
	
	public CrapCriteria<? extends Object> addRestriction(Object obj, OperMode operMode, String[] params) {
		if(params!=null && params.length!=0 && operMode!=null){
		try {
			for(String param : params){
				Object value = BeanInvokeUtils.invokeMethod(obj, param);
				if(value!=null && !value.toString().equals("")){
					if(operMode.equals(OperMode.EQ)){
						count.add(Restrictions.eq(param, value));
						query.add(Restrictions.eq(param, value));
					}
					if(operMode.equals(OperMode.LIKE)){
						if( value instanceof String){
							count.add(Restrictions.like(param, "%"+value+"%"));
							query.add(Restrictions.like(param, "%"+value+"%"));
						} else {
							count.add(Restrictions.eq(param, value));
							query.add(Restrictions.eq(param, value));
						}
					}
				}
			}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return new CrapCriteria<>(query, count);
	}
}

