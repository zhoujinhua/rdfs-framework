package com.rdfs.framework.auth.service;

import com.rdfs.framework.auth.entity.SyResource;
import com.rdfs.framework.hibernate.bean.Page;
import com.rdfs.framework.hibernate.service.HibernateService;

public interface ResourceService extends HibernateService {

	Page pageList(SyResource resource, Page page);

	void saveResource(SyResource resource);

}
