package com.cristik.boot.test.kafka;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author cristik
 */
public class KafkaTest {

    @Test
    public void testProducer() throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "47.245.59.246:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        String topic = "price_test";
        Product product = new Product().setProduct("test")
                .setRegions(Arrays.asList("cn-beijing"))
                .setTaskId("80b07063-dfc6-4cf8-b9c6-7e269d667d1f");
        String target = JSONObject.toJSONString(product);
        Future<RecordMetadata> future = producer.send(new ProducerRecord<>(topic, target));
        while (!future.isDone()) {
            Thread.sleep(3000);
        }
    }

    @Data
    @Accessors(chain = true)
    public static class Product {
        String product;
        List<String> regions;
        String taskId;
    }

}
