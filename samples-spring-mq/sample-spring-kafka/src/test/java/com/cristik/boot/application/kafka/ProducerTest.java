package com.cristik.boot.application.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    Producer producer;

    @Test
    public void sendMessage() throws Exception {
        producer.sendMessage("aa");
    }

}