package com.immoc.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/env")
@RefreshScope // 生效自动读取配置文件
public class ConfigTestController {

    @Value("${env}")
    private String env;

    @GetMapping("/print")
    public String getConfiguration() {
        return env;
    }
}
