package com.cristik.boot.samples.mongodb;

import com.google.common.base.Stopwatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author cristik
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void test() {
        Long cost = 0L;
        for (int i = 0; i < 20; i++) {
            Stopwatch stopwatch = Stopwatch.createStarted();
            mongoTemplate.executeQuery(new Query(), "testCase", document -> {
                System.out.println(document);
            });
            stopwatch.stop();
            cost += stopwatch.elapsed(TimeUnit.MILLISECONDS);
        }
        System.out.println(cost / 20);
    }

}
