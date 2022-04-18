package com.cristik.sample.log4j2.connector.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cristik
 */

@RestController
@RequestMapping("/ssl")
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "hello world";
    }

}
