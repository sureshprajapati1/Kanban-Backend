package com.stackroute.emailsender.repositry;


import com.stackroute.emailsender.model.PasswordDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasswordRepositry extends MongoRepository<PasswordDetail,String> {

}
