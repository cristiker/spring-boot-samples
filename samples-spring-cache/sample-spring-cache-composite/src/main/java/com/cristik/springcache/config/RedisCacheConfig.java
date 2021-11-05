package com.cristik.springcache.config;

import com.cristik.utils.lang.StringUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author cristik
 */

@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class RedisCacheConfig {

    private CacheProperties cacheProperties;

    public RedisCacheConfig(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    /**
     * 配置CacheManager 管理cache
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @Qualifier("redisCacheManager")
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory
            , CacheManagerCustomizers cacheManagerCustomizers) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration());
        List<String> cacheNames = this.cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            builder.initialCacheNames(new LinkedHashSet<>(cacheNames));
        }
        return cacheManagerCustomizers.customize(builder.build());
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        RedisCacheConfiguration redisCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfig.entryTtl(Duration.ofSeconds(60 * 30));
        redisCacheConfig.serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()));
        redisCacheConfig.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new JdkSerializationRedisSerializer()));
        return redisCacheConfig;

    }

    @Bean
    public CacheManagerCustomizers cacheManagerCustomizers(ObjectProvider<List<CacheManagerCustomizer<?>>> customizers) {
        return new CacheManagerCustomizers(customizers.getIfAvailable());
    }

    @Configuration
    public static class RedisConfig {
        RedisProperties redisProperties;

        public RedisConfig(RedisProperties redisProperties) {
            this.redisProperties = redisProperties;
        }

        @Bean
        public JedisPoolConfig jedisPoolConfig() {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(20);
            jedisPoolConfig.setMaxWaitMillis(1000);
            jedisPoolConfig.setTestOnBorrow(true);
            return jedisPoolConfig;
        }


        @Bean
        public JedisConnectionFactory jedisConnectionFactory() {
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration());
            jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
            return jedisConnectionFactory;
        }

        @Bean
        public RedisStandaloneConfiguration redisStandaloneConfiguration() {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setHostName(redisProperties.getHost());
            if (StringUtil.isNoneBlank(redisProperties.getPassword())) {
                RedisPassword redisPassword = RedisPassword.of(redisProperties.getPassword());
                redisStandaloneConfiguration.setPassword(redisPassword);
            }
            redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
            redisStandaloneConfiguration.setPort(redisProperties.getPort());
            return redisStandaloneConfiguration;
        }

    }

}