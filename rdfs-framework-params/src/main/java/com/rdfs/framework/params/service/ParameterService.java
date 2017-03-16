package com.rdfs.framework.params.service;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.params.entity.SyParameter;

public interface ParameterService extends HibernateService {

	SyParameter saveParameter(SyParameter param);

}
