package com.cristik.boot.samples.junit.boot.junit;

import com.cristik.boot.samples.junit.boot.TestConfigurations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author cristik
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfigurations.class)
public class ApplicationRandomPortTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void exampleTest() {
        logger.info("this is china");
    }

}
