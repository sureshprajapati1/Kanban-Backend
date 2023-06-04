package com.stackroute.login.controller;

import com.stackroute.login.exception.UserAlreadyExistingException;
import com.stackroute.login.exception.UserNotFoundException;
import com.stackroute.login.feignClient.SignUp;
import com.stackroute.login.model.User;
import com.stackroute.login.services.TokenGenerator;
import com.stackroute.login.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenGenerator tokenGenerator;


    //    http://localhost:9999/login/registeruser
    @PostMapping("/registeruser")
    public ResponseEntity<?> registerUser(@RequestBody SignUp signUp) throws UserAlreadyExistingException {
        try {
//            signUp.setRole("role-user");
            return new ResponseEntity<>(userService.RegisterUser(signUp), HttpStatus.OK);
        }
        catch (UserAlreadyExistingException ex){
            throw new UserAlreadyExistingException();
        }
    }

    //    http://localhost:9999/login/logincheck
    @PostMapping("/logincheck")
    public ResponseEntity<?> logincheck(@RequestBody User user){
        User result=userService.login(user.getEmailId(), user.getPassword());
        if (result!=null){
            System.out.println(result);
            result.setPassword("");
//            return new ResponseEntity<>(result,HttpStatus.OK);
            return new ResponseEntity<>(tokenGenerator.generatejwt(result),HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Login Failed Email or Password Wrong",HttpStatus.OK);
        }
    }


    //    http://localhost:9999/login/getpassword2/{xxxx}
    @GetMapping("/getpassword2/{emailId}")
    public ResponseEntity<?> GetPassword2(@PathVariable String emailId) throws UserNotFoundException {
        try {
            return new ResponseEntity<>(userService.getPassword2(emailId), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    //    http://localhost:9999/login/changepassword/{xxxx}
    @PostMapping("/changepassword/{emailId}/{oldPassword}/{newPassword}")
    public ResponseEntity<?> ChangePassword(@PathVariable String emailId,@PathVariable String oldPassword,@PathVariable String newPassword) throws UserNotFoundException {
        try {
            return new ResponseEntity<>(userService.changePassword(emailId, oldPassword, newPassword), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
