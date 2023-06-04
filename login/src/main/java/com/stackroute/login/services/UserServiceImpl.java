package com.stackroute.login.services;

import com.stackroute.login.config.PasswordDetail;
import com.stackroute.login.config.Producer;
import com.stackroute.login.exception.UserAlreadyExistingException;
import com.stackroute.login.exception.UserNotFoundException;
import com.stackroute.login.feignClient.*;
import com.stackroute.login.model.User;
import com.stackroute.login.repositry.UserRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepositry userRepositry;
    @Autowired
    private UserProxy userProxy;
@Autowired
private Producer producer;

    @Override
    public User RegisterUser(SignUp signUp) throws UserAlreadyExistingException {
        if (userRepositry.findById(signUp.getEmailId()).isEmpty()) {
            UserDto userDto = new UserDto(signUp.getEmailId(), signUp.getName(),signUp.getPhoneNumber(), signUp.getAddress());
            ResponseEntity responseEntity=
            userProxy.sendUserDtoToKanbanDetailsApp(userDto);
            System.out.println(responseEntity);
            User user = new User(signUp.getEmailId(), signUp.getPassword());
            return userRepositry.save(user);
        }
        else {
            throw new UserAlreadyExistingException();
        }
    }

    @Override
    public User login(String emailId, String password) {
        return userRepositry.findByEmailIdAndPassword(emailId,password);
    }



    @Override
    public String getPassword2(String emailId) throws UserNotFoundException {
        if (userRepositry.findById(emailId).isPresent()) {
            User use = userRepositry.findById(emailId).get();
            System.out.println(use);
            String pass = use.getPassword();
            PasswordDetail passwordDetail = new PasswordDetail(use.getEmailId(), pass, "YOU PASSWORD HAS SEND SUCCESSFULLY", "KANBAN APPLICATION");
            producer.sendMessageToRabbitMq(passwordDetail);
            return pass;
        }
        else
        {
            throw new UserNotFoundException();
        }
    }

    @Override
    public String changePassword(String emailId, String oldPassword, String newPassword) throws UserNotFoundException {
        if (userRepositry.findById(emailId).isPresent()) {
            User use = userRepositry.findById(emailId).get();
           if (use.getPassword().equals(oldPassword)){
                 use.setPassword(newPassword);
               userRepositry.save(use);
               return "password changed successfully";
           }
           else {
               return "enter the correct old password";
           }
        }
        else
        {
            throw new UserNotFoundException();
        }
    }

}
