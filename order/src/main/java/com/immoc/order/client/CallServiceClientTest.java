package com.immoc.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="EUREKA")
public interface CallServiceClientTest {

    @GetMapping("/server/msg")
    String getServerMsg();
}
