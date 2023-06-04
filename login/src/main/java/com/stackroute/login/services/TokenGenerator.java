package com.stackroute.login.services;

import com.stackroute.login.model.User;

import java.util.Map;

public interface TokenGenerator {
    public abstract Map<String ,String> generatejwt(User user);
}
