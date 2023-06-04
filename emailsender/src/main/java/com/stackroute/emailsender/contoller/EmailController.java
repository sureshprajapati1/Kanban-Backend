package com.stackroute.emailsender.contoller;

import com.stackroute.emailsender.model.EmailDetails;

import com.stackroute.emailsender.model.PasswordDetail;
import com.stackroute.emailsender.model.PasswordDetail;
import com.stackroute.emailsender.service.EmailPasswordService;
import com.stackroute.emailsender.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailPasswordService emailPasswordService;

    //    http://localhost:7575/sendmail
    @PostMapping("/sendmail")
    public ResponseEntity<?> sendMail( @RequestBody EmailDetails emailDetails)
    {

        emailService.sendMail(emailDetails);
        return new ResponseEntity<>("The Mail Send Successfully", HttpStatus.OK);
    }


        //    http://localhost:7575/sendmailforpassword
    @PostMapping("/sendmailforpassword")
    public ResponseEntity<?> sendMailForPassword( @RequestBody PasswordDetail password)
    {

        emailPasswordService.SendPasswordAsMail(password);
        return new ResponseEntity<>("The Mail Send Successfully", HttpStatus.OK);
    }


}
