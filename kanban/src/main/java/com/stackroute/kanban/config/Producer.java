package com.stackroute.kanban.config;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
@Autowired
    private RabbitTemplate rabbitTemplate;
@Autowired
    private DirectExchange exchange;



    public void sendMessageToRabbitMq(EmailDetails emailDetails)
    {
        rabbitTemplate.convertAndSend(exchange.getName(),"mail_queue3",emailDetails);
    }
}
