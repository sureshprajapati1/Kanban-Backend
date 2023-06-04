package com.stackroute.emailsender.rabbitMq;

import com.stackroute.emailsender.model.EmailDetails;


import com.stackroute.emailsender.model.PasswordDetail;
import com.stackroute.emailsender.rabitMqForPasswordEmail.Password;
import com.stackroute.emailsender.service.EmailPasswordService;
import com.stackroute.emailsender.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailConsumer {
    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "mail_queue3")
    public void getmailFromToDto(EmailDto emailDto){
        EmailDetails emailDetails=new EmailDetails(emailDto.getEmailId(), emailDto.getMessage(),null ,emailDto.getSubject() );
        emailService.sendMail(emailDetails);
    }

    @Autowired
    private EmailPasswordService emailPasswordService;

    @RabbitListener(queues ="mail_queue5")
    public void getmailFromToDtoForPassword(Password password){
        PasswordDetail passwordDetails=new PasswordDetail(password.getEmailId(), password.getPassword(), password.getMessage(), password.getSubject());
        emailPasswordService.SendPasswordAsMail(passwordDetails);
    }




}
