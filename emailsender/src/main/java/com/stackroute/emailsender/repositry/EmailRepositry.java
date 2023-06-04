package com.stackroute.emailsender.repositry;

import com.stackroute.emailsender.model.EmailDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmailRepositry extends MongoRepository<EmailDetails,String> {

}
