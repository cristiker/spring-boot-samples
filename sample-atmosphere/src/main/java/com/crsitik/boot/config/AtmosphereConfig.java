package com.crsitik.boot.config;

import org.atmosphere.cpr.ApplicationConfig;
import org.atmosphere.cpr.AtmosphereServlet;
import org.atmosphere.cpr.ContainerInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Collections;

/**
 * @author cristik
 */

@Configuration
public class AtmosphereConfig {

    @Bean
    public EmbeddedAtmosphereInitializer atmosphereInitializer() {
        return new EmbeddedAtmosphereInitializer();
    }

    @Bean
    public ServletRegistrationBean<AtmosphereServlet> atmosphereServlet() {
        AtmosphereServlet atmosphereServlet = new AtmosphereServlet();
        ServletRegistrationBean<AtmosphereServlet> registration = new ServletRegistrationBean<>(
                atmosphereServlet, "/websocket/*");
        registration.addInitParameter(ApplicationConfig.ANNOTATION_PACKAGE, "com.cristik.boot.application");
        registration.addInitParameter(ApplicationConfig.CLIENT_HEARTBEAT_INTERVAL_IN_SECONDS, "10");
//        registration.addInitParameter(ApplicationConfig.ATMOSPHERE_HANDLER_PATH,"");
        registration.setLoadOnStartup(1);
        // Need to occur before the EmbeddedAtmosphereInitializer
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    @Configuration
    static class MvcConfiguration implements WebMvcConfigurer {

        @Autowired
        FreeMarkerViewResolver freeMarkerViewResolver;

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/").setViewName("forward:/home/home.html");
        }

        @Override
        public void configureViewResolvers(ViewResolverRegistry registry) {
            registry.viewResolver(freeMarkerViewResolver);
        }
    }

    private static class EmbeddedAtmosphereInitializer extends ContainerInitializer
            implements ServletContextInitializer {

        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            onStartup(Collections.emptySet(), servletContext);
        }

    }

}
