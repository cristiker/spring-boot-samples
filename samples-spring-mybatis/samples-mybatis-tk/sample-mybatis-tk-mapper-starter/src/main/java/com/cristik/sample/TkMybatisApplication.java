package com.cristik.sample;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhenghua.ni
 */
@SpringBootApplication
public class TkMybatisApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(TkMybatisApplication.class).run();
    }

}