package com.cristik.caffeine;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author cristik
 */
@EnableCaching
@SpringBootApplication
public class CaffeineApplication {
    public static void main(String[] args) {
        SpringApplication.run(CaffeineApplication.class, args);
    }

    @Bean
    public CacheManagerCustomizers cacheManagerCustomizers(ObjectProvider<List<CacheManagerCustomizer<?>>> customizers) {
        return new CacheManagerCustomizers(customizers.getIfAvailable());
    }
}