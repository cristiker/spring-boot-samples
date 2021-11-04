package com.cristik.boot.config;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Jetty自定义配置
 *
 * @author cristik
 */

@Configuration
public class JettyConfiguration {

    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory() {
        JettyServletWebServerFactory jettyServletWebServerFactory = new JettyServletWebServerFactory();
        return jettyServletWebServerFactory;
    }

}
