package com.stackroute.emailsender.service;

import com.stackroute.emailsender.model.EmailDetails;
import com.stackroute.emailsender.model.PasswordDetail;
import com.stackroute.emailsender.repositry.EmailRepositry;
import com.stackroute.emailsender.repositry.PasswordRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailPasswordServiceImpl implements EmailPasswordService{
     @Autowired
     private JavaMailSender javaMailSender;
     @Value("${spring.mail.username}")
     private String sender;



    @Autowired
    private PasswordRepositry passwordRepositry;


    @Override
    public PasswordDetail SendPasswordAsMail(PasswordDetail password) {
        SimpleMailMessage mailMessage1=new SimpleMailMessage();
        mailMessage1.setFrom(sender);
        mailMessage1.setTo(password.getEmailId());
        mailMessage1.setText("YOUR KANBAN PASSWORD IS:"+password.getPassword());
//        mailMessage1.setText(password.getMessage());
        mailMessage1.setSubject(password.getSubject());
        javaMailSender.send(mailMessage1);
        return passwordRepositry.save(password);
    }



}
