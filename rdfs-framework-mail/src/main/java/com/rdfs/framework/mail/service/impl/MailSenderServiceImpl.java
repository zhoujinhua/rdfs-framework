package com.rdfs.framework.mail.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.hibernate.service.impl.HibernateServiceSupport;
import com.rdfs.framework.mail.entity.SyMailSender;
import com.rdfs.framework.mail.service.MailSenderService;

@Service
public class MailSenderServiceImpl extends HibernateServiceSupport implements MailSenderService{

	@Override
	public void updateDefault(SyMailSender sender) {
		SyMailSender mailSender = getDefaultSender();
		if(mailSender!=null){
			throw new RuntimeException("默认发件人已存在.");
		}
		sender = getEntityById(SyMailSender.class, sender.getId(), true);
		sender.setIsDefault(Constants.IS.YES);
	}

	@Override
	public void saveMailSender(SyMailSender sender) {
		if(sender.getId()!=null){
			updateEntity(sender, "userName", "password", "status", "remark", "email");
		} else {
			sender.setCreateTime(new Date());
			saveEntity(sender);
		}
	}

	@Override
	public SyMailSender getDefaultSender(){
		String hql = "from SyMailSender where isDefault = '" + Constants.IS.YES +"' and status = '" + Constants.IS.YES +"'";
		List<SyMailSender> mailSenders = getList(hql);
		if(mailSenders!=null && !mailSenders.isEmpty()){
			return mailSenders.get(0);
		}
		return null;
	}
}
