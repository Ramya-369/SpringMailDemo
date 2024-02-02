package MailDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import MailDemo.email.EmailSenderService;
import jakarta.mail.MessagingException;

@SpringBootApplication
public class MailDemoApplication {
	
	@Autowired
	private EmailSenderService senderService;

	public static void main(String[] args) {
		SpringApplication.run(MailDemoApplication.class, args);
	}
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() throws MessagingException{
		
		senderService.sendEmailWithAttachment("ramyamenda999@gmail.com","s170225@rguktsklm.ac.in","This is email body attachment",
				"This email has attachment","Ramya Resume.pdf");
	}

}

