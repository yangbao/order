package com.immoc.order.message.stream;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
//@EnableBinding 注解用来指定一个或多个定义了 @Input 或 @Output 注解的接口，以此实现对消息通道（Channel）的绑定。
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

//@StreamListener，主要定义在方法上，作用是将被修饰的方法注册为消息中间件上数据流的事件监听器，
// 注解中的属性值对应了监听的消息通道名。上面我们将 receive 方法注册为 myInput 消息通道的监听处理器，
// 当我们往这个消息通道发送信息的时候，receiver 方法会执行。
    @StreamListener(StreamClient.INPUT)
    @SendTo(StreamClient.RESPONSE_INPUT)//返回消息
    public String receive(String message) {
        log.info("StreamReceiver: {}", message);
        return  "Got it!";
    }
    //用于接收返回的消息
    @StreamListener(StreamClient.RESPONSE_INPUT)
    public void receiveResponse(String message) {
        log.info("接受到的response: {}", message);
    }


}
