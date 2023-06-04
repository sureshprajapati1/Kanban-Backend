package com.stackroute.login.repositry;

import com.stackroute.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositry extends JpaRepository<User,String> {
    public abstract User findByEmailIdAndPassword(String emailId,String password);

}
