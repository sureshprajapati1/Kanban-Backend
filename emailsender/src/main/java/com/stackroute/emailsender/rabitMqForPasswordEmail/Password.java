package com.stackroute.emailsender.rabitMqForPasswordEmail;
//package com.stackroute.emailsender.rabbitMq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Password {
    private String emailId;
    private String password,message,subject;
}