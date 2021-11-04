package com.cristik.boot.application.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

/**
 * @author cristik
 */
public class ProducerHandlerListener implements ProducerListener {

    //    @Override
    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        System.out.println("成功");
    }

    //    @Override
    public void onError(String topic, Integer partition, Object key, Object value, Exception exception) {
        System.out.println("失败");
    }

    //    @Override
    public boolean isInterestedInSuccess() {
        return false;
    }

    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {

    }

    @Override
    public void onError(ProducerRecord producerRecord, Exception exception) {

    }
}
