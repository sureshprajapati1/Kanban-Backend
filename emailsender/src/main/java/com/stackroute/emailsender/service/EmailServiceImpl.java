package com.stackroute.emailsender.service;

import com.stackroute.emailsender.model.EmailDetails;


import com.stackroute.emailsender.repositry.EmailRepositry;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{
     @Autowired
     private JavaMailSender javaMailSender;
     @Value("${spring.mail.username}")
     private String sender;
    @Autowired
    private EmailRepositry emailRepositry;

    @Override
    public EmailDetails sendMail(EmailDetails emailDetails) {

      SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(sender);
        mailMessage.setTo(emailDetails.getEmailId());
        mailMessage.setText(emailDetails.getMessage());
        mailMessage.setSubject(emailDetails.getSubject());
        javaMailSender.send(mailMessage);
        return emailRepositry.save(emailDetails);
    }



}
