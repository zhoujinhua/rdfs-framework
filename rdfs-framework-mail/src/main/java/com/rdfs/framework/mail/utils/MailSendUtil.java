package com.rdfs.framework.mail.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdfs.framework.core.contants.Constants;
import com.rdfs.framework.core.spring.SpringContextBeanFactory;
import com.rdfs.framework.hibernate.utils.JacksonUtil;
import com.rdfs.framework.mail.config.MailSender;
import com.rdfs.framework.mail.config.MailSenderConfig;
import com.rdfs.framework.mail.entity.Attachment;
import com.rdfs.framework.mail.entity.SyMailLog;
import com.rdfs.framework.mail.entity.SyMailSender;
import com.rdfs.framework.mail.service.MailSenderService;


public class MailSendUtil {

	private static final String SMTP_MAIL_HOST = "smtp.exmail.qq.com";
	private String subject;
	private String content;
	private List<String> mailAddress;
	private List<String> mailCCAddress;
	private List<Attachment> attachments;
	private String userName;
	private String password;
	private MailSenderService mailSenderService = SpringContextBeanFactory.getBean("mailSenderServiceImpl");
	private Logger logger = LoggerFactory.getLogger(MailSendUtil.class);

	public MailSendUtil(String subject, String content, List<String> mailAddress, List<String> mailCCAddress,
			List<Attachment> attachments, String userName, String password) {
		super();
		this.subject = subject;
		this.content = content;
		this.mailAddress = mailAddress;
		this.mailCCAddress = mailCCAddress;
		this.attachments = attachments;
		this.userName = userName;
		this.password = password;
	}

	public MailSendUtil(String subject, String content, List<String> mailAddress, String userName, String password) {
		super();
		this.subject = subject;
		this.content = content;
		this.mailAddress = mailAddress;
		this.userName = userName;
		this.password = password;
	}

	public MailSendUtil(String subject, String content, List<String> mailAddress) {
		super();
		this.subject = subject;
		this.content = content;
		this.mailAddress = mailAddress;
	}

	public MailSendUtil() {
		super();
	}

	public void send() {
		SyMailLog mailLog = null;
		MailSenderConfig config = null;
		try{
			config = getConfig();
			MailSender ms = new MailSender(config);
			MimeMessage message = ms.getMessage();
			ms.setMessage(message);
			
			ms.send();
			mailLog = new SyMailLog(config.getFromMail(), JacksonUtil.toJson(mailAddress), 
					JacksonUtil.toJson(attachments), content, Constants.IS.YES, null, new Date());
		}catch(Exception e){
			logger.error("发送邮件失败,", e);
			mailLog = new SyMailLog(config.getFromMail(), JacksonUtil.toJson(mailAddress), 
					JacksonUtil.toJson(attachments), content, Constants.IS.NO, e.getMessage(), new Date());
		}finally{
			mailSenderService.saveEntity(mailLog);
		}
	}
	
	public MailSenderConfig getConfig(){
		MailSenderConfig c = new MailSenderConfig(SMTP_MAIL_HOST, subject, content, userName);
		if(userName == null && password == null){
			SyMailSender mailSender = mailSenderService.getDefaultSender();
			if(mailSender == null){
				throw new RuntimeException("必须指定发件人.");
			}
			c.setUsername(mailSender.getEmail());
			c.setPassword(mailSender.getPassword());
		} else {
			c.setUsername(userName);
			c.setPassword(password);
		}
		if(mailAddress!=null && !mailAddress.isEmpty()){
			for(String addr : mailAddress){
				c.addToMail(addr);
			}
		}
		if(mailCCAddress!=null && !mailCCAddress.isEmpty()){
			for(String addr : mailCCAddress){
				c.addCcMail(addr);
			}
		}
		if(attachments!=null && !attachments.isEmpty()){
			for(Attachment attachment : attachments){
				c.addAttachment(attachment);
			}
		}
		return c;
	}

	public static void main(String[] args) throws Exception {
		List<String> list = new ArrayList<>();
		list.add("fei.zhou01@liyunqiche.com");
		
		List<Attachment> attachments
		 = new ArrayList<>();
		
		attachments.add(new Attachment(new File("F://test.xlsx")));
		new MailSendUtil("a", "b", list, null, attachments, "noreply@liyunqiche.com", "Password2").send();
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(List<String> mailAddress) {
		this.mailAddress = mailAddress;
	}

	public List<String> getMailCCAddress() {
		return mailCCAddress;
	}

	public void setMailCCAddress(List<String> mailCCAddress) {
		this.mailCCAddress = mailCCAddress;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
