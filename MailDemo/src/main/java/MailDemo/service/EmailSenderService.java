package MailDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
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
            // Load the attachment file from the classpath
            Resource resource = new ClassPathResource(details.getAttachment());
            
            
            if (resource.exists()) {
                // Add the attachment
                mimeMessageHelper.addAttachment(resource.getFilename(), resource.getFile());
            } else {
                // Handle the case where the resource does not exist
                System.out.println("Attachment file '" + details.getAttachment() + "' does not exist in the classpath.");
                return "Error: Attachment file not found";
            }

            mailSender.send(mimeMessage);
            return "Mail sent successfully...";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error while sending email";
        }
    }
    
   @Scheduled(cron="0 01 11 * * ?")  // Schedule task
    public void sendScheduledEmail() {
        EmailDetails details = new EmailDetails();
        details.setRecipient("ramyamenda999@gmail.com");
        details.setSubject("Scheduled Email");
        details.setMsgBody("This is a scheduled email dummy.");
        sendSimpleMail(details);
    }


} 
