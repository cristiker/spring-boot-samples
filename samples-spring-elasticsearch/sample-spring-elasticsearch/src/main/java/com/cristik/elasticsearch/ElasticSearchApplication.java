package com.cristik.elasticsearch;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * .@author cristik
 */
@SpringBootApplication
public class ElasticSearchApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ElasticSearchApplication.class).run(args);
    }

}