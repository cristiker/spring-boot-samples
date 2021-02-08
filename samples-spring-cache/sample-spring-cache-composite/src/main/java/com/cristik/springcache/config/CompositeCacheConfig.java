package com.cristik.springcache.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author cristik
 */
@Configuration
public class CompositeCacheConfig {

    @Resource(name = "redisCacheManager")
    CacheManager redisCacheManager;

    @Resource(name = "caffeineCacheManager")
    CacheManager caffeineCacheManager;

    @Bean
    @Primary
    public CacheManager cacheManager() {
        CompositeCacheManager compositeCacheManager = new CompositeCacheManager();
        compositeCacheManager.setCacheManagers(Arrays.asList(redisCacheManager, caffeineCacheManager));
        return compositeCacheManager;
    }

}