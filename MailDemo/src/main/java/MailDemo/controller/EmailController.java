package MailDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import MailDemo.entity.EmailDetails;
import MailDemo.service.EmailSenderService;

@RestController
public class EmailController {

	@Autowired
	private EmailSenderService emailSenderService;

	@PostMapping("/sendMail")
	public String sendSimpleMail(@RequestBody EmailDetails details) {
		String status = emailSenderService.sendSimpleMail(details);
		return status;
	}

	@PostMapping("/sendMailWithAttachment")
	public String sendMailWithAttachment(@RequestBody EmailDetails details) {
		String status = emailSenderService.sendEmailWithAttachment(details);
		return status;
	}
	
	@Scheduled(cron = "0 30 11 * * ?")
	@PostMapping("/sendScheduledEmailManually")
	public String sendScheduledEmailManually() {
	    emailSenderService.sendScheduledEmail();
	    return "Scheduled email sent manually.";
	}


}
