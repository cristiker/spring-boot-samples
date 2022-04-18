package com.cristik.sample.log4j2.junit.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cristik
 */

@Configuration
public class TestConfigurations {

    @Bean
    public String add() {
        return "xx";
    }

}
