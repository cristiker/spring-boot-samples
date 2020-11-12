package com.cristik.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cristik
 */

@SpringBootApplication
public class Log4j2Application {

    private static final Logger logger = LoggerFactory.getLogger(Log4j2Application.class);

    public static void main(String[] args) {
        logger.error("aabbccddee");
        SpringApplication.run(Log4j2Application.class, args);
        logger.info("test");
    }

}
