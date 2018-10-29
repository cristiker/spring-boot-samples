package com.cristik.boot.config;

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cristik
 */

@Configuration
public class JettyConfig {

    @Bean
    public JettyServletWebServerFactory jettyServletWebServerFactory() {
        JettyServletWebServerFactory jettyServletWebServerFactory = new JettyServletWebServerFactory();
        return jettyServletWebServerFactory;
    }

}
