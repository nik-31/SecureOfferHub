package com.project_nikhil.SecureOfferHub.service;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String content) throws MessagingException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(content);
        javaMailSender.send(mail);
    }
}
