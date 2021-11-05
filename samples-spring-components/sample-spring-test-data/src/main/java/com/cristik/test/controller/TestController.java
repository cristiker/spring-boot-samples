package com.cristik.test.controller;

import com.alibaba.fastjson.JSON;
import com.cristik.sample.framework.response.message.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nizhenghua
 * @date 2021/11/05
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @GetMapping(value = "/message")
    public ResponseData<String> getMessage(String message) {
        log.info("TestController#getMessage parameter:{}", message);
        ResponseData<String> response = ResponseData.success(message);
        log.info("TestController#getMessage parameter:{} response:{}", message, JSON.toJSONString(response));
        return response;
    }

}
