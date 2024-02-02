package MailDemo.email;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body) {
     
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ramyamenda369@gmail.com");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);

            mailSender.send(message);
            System.out.println("Mail sent successfully...");
        
    }
    
    public void sendEmailWithAttachment(
    		String toEmail,String ccEmail,String body,String subject,String attachment)throws MessagingException{
    	
    	MimeMessage mimeMessage=mailSender.createMimeMessage();
    	MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
    	
    	mimeMessageHelper.setFrom("ramyamenda369@gmail.com");
    	mimeMessageHelper.setTo(toEmail);
    	mimeMessageHelper.setCc(ccEmail);
    	mimeMessageHelper.setText(body);
    	mimeMessageHelper.setSubject(subject);
    	
    	FileSystemResource fileSystem=new FileSystemResource(new File(attachment));
    	mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);
    	
    	mailSender.send(mimeMessage);
    	System.out.println("Mail send...");
    }
}
