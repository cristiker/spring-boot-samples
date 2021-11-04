package com.cristik.samples.redisson.delayqueue;

import org.redisson.api.RBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @author nizhenghua
 * @date 2021/09/09
 */
public class DelayQueueListener {

    private static final Logger log = LoggerFactory.getLogger(DelayQueueListener.class);

    @Autowired
    private RBlockingQueue<String> blockingQueue;

    public void consume(String message) {
        log.info(message);
    }

    @PostConstruct
    public void init() {
        new Thread(() -> {
            while (true) {
                try {
                    String message = blockingQueue.take();
                    consume(message);
                } catch (InterruptedException e) {
                    log.error("DelayQueueListener consume failed", e);
                }

            }
        }).start();

    }

}
