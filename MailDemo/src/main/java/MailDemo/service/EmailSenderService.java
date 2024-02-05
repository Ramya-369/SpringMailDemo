package MailDemo.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import MailDemo.entity.EmailDetails;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;
    
    public String sendSimpleMail(EmailDetails details) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(details.getRecipient());
            message.setSubject(details.getSubject());
            message.setText(details.getMsgBody());

            mailSender.send(message);
            return "Mail sent successfully...";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while sending email";
        }
    }
    
  
    public String sendEmailWithAttachment(EmailDetails details) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());
            
            FileSystemResource fileSystem = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(fileSystem.getFilename(), fileSystem);
            
            mailSender.send(mimeMessage);
            return "Mail sent successfully...";
        } catch(Exception e) {
            e.printStackTrace();
            return "Error while sending email";
        }
    }
}
