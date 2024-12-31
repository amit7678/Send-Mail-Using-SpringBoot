package com.send.JavaMail.controller;
import com.send.JavaMail.entity.EmailRequest;
import com.send.JavaMail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-mail")
    public ResponseEntity<String>sendEmail(@RequestBody EmailRequest emailRequest)
    {
        try{

            emailService.sendSimpleMail(emailRequest);
            return new ResponseEntity<>("mail has been send successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return  new ResponseEntity<>("failed to send email server error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/email-with-attachment")
    public ResponseEntity<?>sendMailWithAttachment( @RequestParam String email, @RequestParam(required = false) MultipartFile[] file)
    {
        try{
            System.out.println("Received email parameter: " + email);
            emailService.sendEmailAndAttachment(email,file);
            return new ResponseEntity<>("mail has been send successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return  new ResponseEntity<>("failed to send email server error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
