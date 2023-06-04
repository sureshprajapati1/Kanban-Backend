package com.stackroute.kanban.repositry;

import com.stackroute.kanban.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDetailsRepositry extends MongoRepository<User,String> {
}
