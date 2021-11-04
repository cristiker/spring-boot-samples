package com.cristik.samples.redisson.controller;

import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhenghua.ni
 */
@RestController
@RequestMapping(value = "/redisson")
public class TestController {

    @Autowired
    private RDelayedQueue<String> delayedQueue;

    @PostMapping(value = "/delay/queue/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public String putMessage(String message) {
        delayedQueue.offer(message, 60, TimeUnit.SECONDS);
        return "";
    }

}
