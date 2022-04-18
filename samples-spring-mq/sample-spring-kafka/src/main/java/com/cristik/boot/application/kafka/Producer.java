package com.cristik.sample.log4j2.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author cristik
 */

@Service
public class Producer {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic}")
    private String kafkaTopic;

    public void sendMessage(String message) {
        kafkaTemplate.send(kafkaTopic, message);
    }

}
