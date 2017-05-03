package com.rdfs.framework.hibernate.support;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rdfs.framework.hibernate.hibernate.CrapSession;


/**
 * hibernate 持久化支持
 * 获取自定义session，service层直接调用
 */
//@Component
public abstract class HibernateSupport {
	
	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;
	
	/**
	 * 获取一个session
	 */
	protected CrapSession getSession(){
		return new CrapSession(this.sessionFactory.getCurrentSession());
	}
	
	/**
	 * 创建一个sesison
	 * @return
	 */
	protected CrapSession createSession(){
		return new CrapSession(this.sessionFactory.openSession());
	}
	
	/**
	 * 获取模型类的信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected ClassMetadata getModelClassInfo(Class type){
		return sessionFactory.getClassMetadata(type);
	}
	
}
