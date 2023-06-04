package com.stackroute.login.services;

import com.stackroute.login.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class TokenGeneratorImpl implements TokenGenerator{
    @Override
    public Map<String, String> generatejwt(User user) {
        Map<String,String> result=new HashMap<String, String>();
        Map<String, Object> userdata=new HashMap<>();
        userdata.put("email",user.getEmailId());
//        userdata.put("role",user.getRole());

        Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Instant expiration = issuedAt.plus(10, ChronoUnit.MINUTES);


        String jwt= Jwts.builder()
                .setClaims(userdata)
                .setIssuedAt(Date.from(issuedAt))
                .setExpiration(Date.from(expiration))
                .setIssuer("MyCompany")
                .signWith(SignatureAlgorithm.HS512,"idontsay")
                .compact();
        result.put("token",jwt);
        result.put("message","user logged in successfully");
//        result.put("role", user.getRole());
        return result;
    }
}
