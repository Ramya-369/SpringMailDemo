package MailDemo.service;

import MailDemo.entity.EmailDetails;

public interface EmailService {
	
	String sendSimpleMail(EmailDetails details);
	
	String sendEmailWithAttachment(EmailDetails details);

}
