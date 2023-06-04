package com.stackroute.emailsender.rabbitMq;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class MessageConfig {
    @Bean
    public Jackson2JsonMessageConverter getMessageConvertor(){
        return new Jackson2JsonMessageConverter();
    }
}
