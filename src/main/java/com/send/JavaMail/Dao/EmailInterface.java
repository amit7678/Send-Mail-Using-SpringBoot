package com.send.JavaMail.Dao;

import com.send.JavaMail.entity.EmailRequest;
import org.springframework.web.multipart.MultipartFile;

public interface EmailInterface {
    public boolean sendSimpleMail(EmailRequest emailRequest) throws Exception;
    public void sendEmailAndAttachment(String email, MultipartFile[] file) throws Exception;

}
