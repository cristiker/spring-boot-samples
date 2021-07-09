package com.cristik.spring.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author zhenghua.ni
 */

@Order(12)
public class TestEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TestEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        logger.info("TestEnvironmentPostProcessor#postProcessEnvironment 已执行");
    }

}