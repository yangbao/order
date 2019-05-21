package com.immoc.order.controller;

import com.immoc.order.dto.OrderDTO;
import com.immoc.order.message.stream.StreamClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;

@RestController
public class SendMessageController {

   @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StreamClient streamClient;

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
    @GetMapping("/sendMessageToStream")
    public void process() {
        String message = "sendMessageToStream -> now " + new Date();
        streamClient.output().send(MessageBuilder.withPayload(message).build());
    }
    /**
     * 发送 orderDTO对象
     */
    @GetMapping("/sendDtoMessage")
    public void processDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("123456");
        streamClient.output().send(MessageBuilder.withPayload(orderDTO).build());
    }
}
