package com.cristik.samples.redisson.controller;

import lombok.Data;
import lombok.experimental.Accessors;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.codec.FstCodec;
import org.redisson.codec.SerializationCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author zhenghua.ni
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private RedissonClient redissonClient;

    @PostMapping(value = "/redisson", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        Student student = new Student()
                .setAge(1)
                .setId(20L)
                .setName("tom");
        RBucket<Student> rBucket = redissonClient.getBucket("aaaa", new FstCodec());
        rBucket.set(student, 60, TimeUnit.SECONDS);
        RBucket<Student> rBucket2 = redissonClient.getBucket("aaaa", new SerializationCodec());
        Student student1 = rBucket2.get();
        return "";
    }


    @Data
    @Accessors(chain = true)
    public static class Student {
        private Long id;
        private String name;
        private Integer age;
    }

}
