package com.cristik.boot.samples.threadpool.samples.pool;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author cristik
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreadPoolTaskExecutorTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @Test
    public void testThread() {
        List<Future> taskFutures = Lists.newArrayList();
        for (int i = 0; i < 50; i++) {
            try {
                taskFutures.add(threadPoolTaskExecutor.submit(new Task()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e.getCause());
            }
        }
        while (true) {
            if (taskFutures.stream().allMatch(future -> future.isDone())) {
                break;
            }
        }

    }


    public static class Task implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println(1);
                Thread.sleep(5000);
                System.out.println(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
