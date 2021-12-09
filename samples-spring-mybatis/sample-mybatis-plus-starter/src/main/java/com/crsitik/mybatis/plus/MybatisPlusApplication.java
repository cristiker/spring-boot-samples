package com.crsitik.mybatis.plus;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author zhenghua.ni
 */
@SpringBootApplication
public class MybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder()
                .sources(MybatisPlusApplication.class)
                .bannerMode(Banner.Mode.CONSOLE);
        springApplicationBuilder.run(args);
    }

}