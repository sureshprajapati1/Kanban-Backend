package com.stackroute.emailsender.service;


import com.stackroute.emailsender.model.PasswordDetail;


import com.stackroute.emailsender.model.PasswordDetail;

public interface EmailPasswordService {


    public abstract PasswordDetail SendPasswordAsMail(PasswordDetail password);
}
