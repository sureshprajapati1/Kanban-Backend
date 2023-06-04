package com.stackroute.login.feignClient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import org.bson.types.Binary;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUp {
    private String emailId,password,name,phoneNumber,address;
//    private Binary image;

}
