package com.immoc.order.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class SendMessageController {

   @Autowired
    private AmqpTemplate amqpTemplate;

   @GetMapping("/sendMsg1")
    public void send() {
        amqpTemplate.convertAndSend("myQueue", "now " + new Date());
    }
    @GetMapping("/sendMsg2")
    public void sendToComputer() {
        amqpTemplate.convertAndSend("orderExchange", "computer","now " + new Date());
    }
    @GetMapping("/sendMsg3")
    public void sendToFruit() {
        amqpTemplate.convertAndSend("orderExchange", "fruit","now " + new Date());
    }
}
