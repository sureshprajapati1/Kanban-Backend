package com.stackroute.emailsender.service;

import com.stackroute.emailsender.model.EmailDetails;
//import com.stackroute.emailsender.model.PasswordDetail;


public interface EmailService {
    public abstract EmailDetails sendMail(EmailDetails emailDetails);


}
