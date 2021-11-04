package com.cristik.boot.application.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProducerTest {

    @Autowired
    Producer producer;

    @Test
    public void sendMessage() throws Exception {
        producer.sendMessage("aa");
    }

}