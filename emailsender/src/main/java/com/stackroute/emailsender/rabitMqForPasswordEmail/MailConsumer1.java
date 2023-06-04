//package com.stackroute.emailsender.rabitMqForPasswordEmail;
//
//import com.stackroute.emailsender.model.PasswordDetail;
//import com.stackroute.emailsender.service.EmailPasswordService;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class MailConsumer1 {
//    @Autowired
//    private EmailPasswordService emailPasswordService;
//
//
//
//
//    @RabbitListener(queues ="mail_queue5")
//    public void getmailFromToDtoForPassword(Password password){
//        PasswordDetail passwordDetails=new PasswordDetail(password.getEmailId(), password.getPassword(), password.getMessage(), password.getSubject());
//        emailPasswordService.SendPasswordAsMail(passwordDetails);
//    }
//
//
//}
