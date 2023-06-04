package com.stackroute.login.services;

import com.stackroute.login.exception.UserAlreadyExistingException;
import com.stackroute.login.exception.UserNotFoundException;
import com.stackroute.login.feignClient.SignUp;
import com.stackroute.login.model.User;

public interface UserService {
    public abstract User RegisterUser(SignUp signUp) throws UserAlreadyExistingException;
    public abstract User login(String emailId, String password);



    public abstract String getPassword2(String emailId) throws UserNotFoundException;

    public abstract  String changePassword(String emailId ,String oldPassword,String newPassword) throws UserNotFoundException;
}
