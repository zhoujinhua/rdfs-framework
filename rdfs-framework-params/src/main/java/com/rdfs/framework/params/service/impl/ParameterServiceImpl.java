package com.rdfs.framework.params.service.impl;

import org.springframework.stereotype.Service;

import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.params.entity.SyParameter;
import com.rdfs.framework.params.service.ParameterService;

@Service
public class ParameterServiceImpl extends HibernateServiceSupport implements ParameterService {

	@Override
	public SyParameter saveParameter(SyParameter param) {
		if(param.getId() == null){
			saveEntity(param);
		} else {
			updateEntity(param);
		}
		return param;
	}

}
