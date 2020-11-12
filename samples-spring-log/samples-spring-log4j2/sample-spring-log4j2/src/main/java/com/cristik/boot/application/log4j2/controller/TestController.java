package com.cristik.boot.application.log4j2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cristik
 */

@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/test/index")
    public String test() {
        logger.debug("this is debug message");
        logger.info("this is info message");
        logger.warn("this is warn message");
        logger.error("this is error message");
        return "hello";
    }

}
