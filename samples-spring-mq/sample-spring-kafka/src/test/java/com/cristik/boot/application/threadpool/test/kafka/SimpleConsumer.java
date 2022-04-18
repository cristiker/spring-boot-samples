package com.cristik.sample.log4j2.threadpool.test.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

/**
 * @author cristik
 */
public class SimpleConsumer {

    public static void main(String[] args) throws InterruptedException {
        Logger logger = LoggerFactory.getLogger(SimpleConsumer.class);

        String topic = "test-topic";// topic name

        Properties props = new Properties();
        props.put("bootstrap.servers", "47.100.212.102:9092");//用于建立与 kafka 集群连接的 host/port 组。
        props.put("group.id", "group.execute");// Consumer Group Name
        props.put("enable.auto.commit", "true");// Consumer 的 offset 是否自动提交
        props.put("auto.commit.interval.ms", "1000");// 自动提交 offset 到 zookeeper 的时间间隔，时间是毫秒
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        Consumer<String, String> consumer = new KafkaConsumer(props);
        consumer.subscribe(Arrays.asList(topic));
        while (true) {
            Thread.sleep(3000);
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                logger.info("{partition = {}, offset = {}, key = {}, value = {}}", record.partition(), record.offset(), record.key(), record.value());
            }
        }
    }

}
