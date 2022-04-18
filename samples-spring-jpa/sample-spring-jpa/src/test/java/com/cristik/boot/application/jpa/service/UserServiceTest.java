package com.cristik.sample.log4j2.jpa.service;

import com.cristik.sample.log4j2.jpa.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author cristik
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    IUserService userService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void before() {
        User user = new User();
        user.setAccount("cristik");
        user.setName("cristik");
        user.setPwd("123456");
        user = userService.insert(user);
        logger.info("user = {}", user.toString());
    }

    @Test
    @Rollback(false)
    public void findAllTest() {
        List<User> users = userService.findByUser(new User());
        logger.info(Arrays.toString(users.toArray()));
    }

    @Test
    @Rollback(false)
    public void queryTest() {
        Page page = userService.findUserByPage(1, 10);
        logger.info("totalPage={},totalElements={}", page.getTotalPages(), page.getTotalElements());
    }


}