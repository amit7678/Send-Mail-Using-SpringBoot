package com.send.JavaMail.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.send.JavaMail.Dao.EmailInterface;
import com.send.JavaMail.entity.EmailRequest;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class EmailService implements EmailInterface {

    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public boolean sendSimpleMail(EmailRequest emailRequest) throws Exception {
        // Create a MimeMessage object, which is used to represent the email message
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message);
        helper.setFrom("amitmaurya9@gmail.com",emailRequest.getTitle());
        String[] recipients = emailRequest.getRecipentEmail();  // assuming getRecipentEmails() returns an array of email addresses
        helper.setTo(recipients);
        helper.setCc(emailRequest.getCc());
        helper.setBcc(emailRequest.getBcc());
        helper.setSubject(emailRequest.getSubject());
        helper.setText(emailRequest.getBody(),true);
        javaMailSender.send(message);
        return true;  // Return true to indicate the email has been sent successfully

    }

    @Override
    public void sendEmailAndAttachment(String email, MultipartFile[] files) throws Exception {
        // Convert the email details from JSON to EmailRequest object
        ObjectMapper mapper=new ObjectMapper();
        EmailRequest emailRequest=mapper.readValue(email, EmailRequest.class);

        // Create a new MimeMessage object for email construction
        MimeMessage message=javaMailSender.createMimeMessage();

        // Create a MimeMessageHelper instance with support for attachments (true)
        MimeMessageHelper helper=new MimeMessageHelper(message,true);

        // Set the sender's email and title
        helper.setFrom("amitmaurya9@gmail.com",emailRequest.getTitle());
        // Get the recipient email addresses from the EmailRequest and set them
        String[] recipients = emailRequest.getRecipentEmail();  // assuming getRecipentEmails() returns an array of email addresses
        helper.setTo(recipients);
        // Set the CC and BCC recipients, if provided in the request
        helper.setCc(emailRequest.getCc());
        helper.setBcc(emailRequest.getBcc());
        helper.setSubject(emailRequest.getSubject());  // Set the subject of the email
        helper.setText(emailRequest.getBody(),true); // Set the body content of the email and specify that it is HTML-formatted (true)
        for (MultipartFile file : files) {
            if (file != null) {  // Convert the MultipartFile into a ByteArrayResource for attachment
                ByteArrayResource byteArrayResource = new ByteArrayResource(file.getBytes());
                // Add the file as an attachment using the original filename
                helper.addAttachment(file.getOriginalFilename(), byteArrayResource);
            }
        }
        javaMailSender.send(message); // Send the email with attachments using JavaMailSender

    }
}
