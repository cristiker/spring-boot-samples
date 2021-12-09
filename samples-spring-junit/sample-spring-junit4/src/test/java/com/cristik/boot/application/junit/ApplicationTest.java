package com.cristik.boot.application.junit;

import com.cristik.boot.application.junit.boot.TestConfigurations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cristik
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfigurations.class)
public class ApplicationTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @LocalServerPort
    private Integer port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void exampleTest() {
        logger.info("current port is {}", port);
    }

}
