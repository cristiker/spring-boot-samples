package com.cristik.sample.log4j2.junit5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author nizhenghua
 * @date 2021/11/04
 */
@SpringBootApplication(scanBasePackages = {"com.cristik"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
