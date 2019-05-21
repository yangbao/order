package com.immoc.order.message;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MqReceiver {

    //1. @RabbitListener(queues = "myQueue") // 不能自动创建队列
    //2. 自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3. 自动创建, Exchange和Queue绑定
    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    public void receiveMessage1(String message) {
        System.out.println("Received1 <" + message + ">");
    }
    //3. 自动创建, Exchange和Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void receiveMessage2(String message) {
        System.out.println("Received2 <" + message + ">");
    }
    //使用exchange的场景,订单服务需要发送消息给2个不通过的服务,用routine key来标识
    //sender往相同的exchange发送,再由exchange分类push到不同的queue
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitQueue"),
            exchange = @Exchange("orderExchange"),
            key = "fruit"
    ))
    public void receiveMessageByFruit(String message) {
        System.out.println("receiveMessageByFruit <" + message + ">");
    }
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("computerQueue"),
            exchange = @Exchange("orderExchange"),
            key = "computer"
    ))
    public void receiveMessageByComputer(String message) {
        System.out.println("receiveMessageByComputer <" + message + ">");
    }
}
