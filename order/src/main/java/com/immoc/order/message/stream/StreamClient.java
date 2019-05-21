package com.immoc.order.message.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * StreamClient 接口，通过 @Input和 @Output注解定义输入通道和输出通道
 */
public interface StreamClient {

    //@Input 和 @Output 注解都还有一个 value 属性，该属性可以用来设置消息通道的名称
    String INPUT = "myInput";
    String OUTPUT = "myOutput";
    //下面2个是消息接收后,返回确认消息
    String RESPONSE_INPUT = "responseInput";
    String RESPONSE_OUTPUT = "responseOutput";

    /* 相对于application 方来说的 输入还是输出
    当定义输出通道的时候，需要返回 MessageChannel 接口对象，
    该接口定义了向消息通道发送消息的方法；定义输入通道时，需要返回 SubscribableChannel 接口对象，
    该接口集成自 MessageChannel 接口，它定义了维护消息通道订阅者的方法。
     */
    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.OUTPUT)
    MessageChannel output();

    //下面2个是消息接收后,返回确认消息
    @Input(StreamClient.RESPONSE_INPUT)
    SubscribableChannel responseInput();

    @Output(StreamClient.RESPONSE_OUTPUT)
    MessageChannel responseOutput();
/*
在完成了消息通道绑定的定义后，这些用于定义绑定消息通道的接口则可以被 @EnableBinding 注解的 value 参数指定，
从而在应用启动的时候实现对定义消息通道的绑定，Spring Cloud Stream 会为其创建具体的实例，
而开发者只需要通过注入的方式来获取这些实例并直接使用即可
*/
}
