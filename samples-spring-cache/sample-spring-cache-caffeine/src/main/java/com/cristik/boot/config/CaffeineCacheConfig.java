package com.cristik.boot.config;

import com.github.benmanes.caffeine.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author cristik
 */

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class CaffeineCacheConfig {

    private final CacheProperties cacheProperties;
    private final Caffeine<Object, Object> caffeine;
    private final CaffeineSpec caffeineSpec;
    private final CacheLoader<Object, Object> cacheLoader;

    public CaffeineCacheConfig(CacheProperties cacheProperties,
                               ObjectProvider<Caffeine<Object, Object>> caffeine,
                               ObjectProvider<CaffeineSpec> caffeineSpec,
                               ObjectProvider<CacheLoader<Object, Object>> cacheLoader) {
        this.cacheProperties = cacheProperties;
        this.caffeine = caffeine.getIfAvailable();
        this.caffeineSpec = caffeineSpec.getIfAvailable();
        this.cacheLoader = cacheLoader.getIfAvailable();
    }

    @Bean
    public CacheManager caffeineCacheManager(CacheManagerCustomizers cacheManagerCustomizers) {
        CaffeineCacheManager cacheManager = createCacheManager();
        List<String> cacheNames = this.cacheProperties.getCacheNames();
        if (!CollectionUtils.isEmpty(cacheNames)) {
            cacheManager.setCacheNames(cacheNames);
        }
        return cacheManagerCustomizers.customize(cacheManager);
    }

    private CaffeineCacheManager createCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        /*setCacheBuilder(cacheManager);*/
        cacheManager.setCaffeine(caffeineCacheBuilder());
        if (this.cacheLoader != null) {
            cacheManager.setCacheLoader(this.cacheLoader);
        }
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(1500)
                .maximumSize(150)
                .expireAfterAccess(30, TimeUnit.MINUTES)
                .weakKeys()
                .removalListener(new PublishRemoveCacheListener())
                .recordStats();
    }

    /**
     * 解析Application.properties中的配置
     *
     * @param cacheManager
     */
    private void setCacheBuilder(CaffeineCacheManager cacheManager) {
        String specification = this.cacheProperties.getCaffeine().getSpec();
        if (StringUtils.hasText(specification)) {
            cacheManager.setCacheSpecification(specification);
        } else if (this.caffeineSpec != null) {
            cacheManager.setCaffeineSpec(this.caffeineSpec);
        } else if (this.caffeine != null) {
            cacheManager.setCaffeine(this.caffeine);
        }
    }

    /**
     * Listen to cache remove event
     */
    static class PublishRemoveCacheListener implements RemovalListener<Object, Object> {

        private static final Logger logger = LoggerFactory.getLogger(PublishRemoveCacheListener.class);

        @Override
        public void onRemoval(@Nullable Object key, @Nullable Object value, @Nonnull RemovalCause cause) {
            // TODO: 2018/11/15 publish the message to other application with redis
            logger.info("key={},value={} is removed", key, value);
        }
    }
}
