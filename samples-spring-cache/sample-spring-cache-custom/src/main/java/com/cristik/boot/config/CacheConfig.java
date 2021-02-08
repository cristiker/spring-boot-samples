package com.cristik.boot.config;

import com.cristik.aop.log.service.impl.CalculatorService;
import com.cristik.aop.log.common.ConcurrentMapCollectionHandlingDecoratedCache;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cristik
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager() {
            @Override
            protected Cache createConcurrentMapCache(String name) {
                return new ConcurrentMapCollectionHandlingDecoratedCache(super.createConcurrentMapCache(name));
            }
        };
    }

    @Bean
    public CalculatorService calculatorService() {
        return new CalculatorService();
    }
}