package com.cristik.sample.log4j2.junit5;

import com.alibaba.fastjson.JSON;
import com.cristik.sample.framework.response.message.ResponseData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author nizhenghua
 * @date 2021/11/04
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test() {
        ResponseEntity<ResponseData> response = restTemplate.getForEntity("localhost:8080/api/v1/test/message", ResponseData.class);
        System.out.println(JSON.toJSONString(response.getBody()));
    }
}
