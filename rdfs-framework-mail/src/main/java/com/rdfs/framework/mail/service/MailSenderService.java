package com.rdfs.framework.mail.service;

import com.rdfs.framework.hibernate.service.HibernateService;
import com.rdfs.framework.mail.entity.SyMailSender;

public interface MailSenderService extends HibernateService{

	void updateDefault(SyMailSender sender);

	void saveMailSender(SyMailSender sender);

	SyMailSender getDefaultSender();

}
