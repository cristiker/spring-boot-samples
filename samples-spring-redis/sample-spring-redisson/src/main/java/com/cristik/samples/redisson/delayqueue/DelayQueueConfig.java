package com.cristik.samples.redisson.delayqueue;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cristik
 * @date 2021/9/9
 */
@Configuration
public class DelayQueueConfig {

    private String delayQueueName = "sample:redisson:delay:queue1";

    @Autowired
    private RedissonClient redissonClient;

    @Bean
    public RBlockingQueue<String> blockingQueue(){
        return redissonClient.getBlockingQueue(delayQueueName);
    }

    @Bean
    public RDelayedQueue<String> delayedQueue(@Qualifier("blockingQueue") RBlockingQueue<String> blockingQueue){
        return redissonClient.getDelayedQueue(blockingQueue);
    }
}
