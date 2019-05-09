package com.immoc.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this only test: called by RestTemplate in client
 */
@RestController
@RequestMapping("/server")
public class ServerTest {

    @GetMapping("/msg")
    public String message() {
        return "this is a test message";
    }
}
