package com.crsitik.boot.config;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.IOException;
import java.util.Properties;

/**
 * @author cristik
 */

@org.springframework.context.annotation.Configuration
public class FreemarkerConfig {

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setCache(true);
        resolver.setSuffix(".html");
        resolver.setPrefix("");
        resolver.setCache(false);
        resolver.setRequestContextAttribute("request");
        resolver.setExposeSpringMacroHelpers(true);
        resolver.setExposeRequestAttributes(true);
        resolver.setExposeSessionAttributes(true);
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        factory.setTemplateLoaderPath("classpath:/views");
        factory.setPreferFileSystemAccess(false);
        factory.setDefaultEncoding("UTF-8");
        Configuration configuration = factory.createConfiguration();
        configuration.setClassicCompatible(true);
        Properties properties = new Properties();
        properties.setProperty("default_encoding", "UTF-8");
        properties.setProperty("locale", "UTF-8");
        properties.setProperty("url_escaping_charset", "UTF-8");
        properties.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
        properties.setProperty("time_format", "HH:mm:ss");
        properties.setProperty("number_format", "0.####");
        properties.setProperty("boolean_format", "true,false");
        properties.setProperty("whitespace_stripping", "true");
        properties.setProperty("tag_syntax", "auto_detect");
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setFreemarkerSettings(properties);
        freeMarkerConfigurer.setConfiguration(configuration);
        return freeMarkerConfigurer;
    }
}
