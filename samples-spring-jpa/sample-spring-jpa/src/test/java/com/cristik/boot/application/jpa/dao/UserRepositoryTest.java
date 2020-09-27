package com.cristik.boot.application.jpa.dao;

import com.cristik.boot.application.jpa.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author cristik
 */

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void before() {
        User user = new User();
        user.setAccount("cristik");
        user.setName("cristik");
        user.setPwd("123456");
        user = userRepository.save(user);
        logger.info("user = {}", user.toString());
    }

    @Test
    @Rollback(false)
    public void findAllTest() {
        List<User> users = userRepository.findAll(Example.of(new User()));
        logger.info(Arrays.toString(users.toArray()));
    }

    @Test
    @Rollback(false)
    public void queryTest() {
        Page page = userRepository.findAll(PageRequest.of(1, 10));
        logger.info("totalPage={},totalElements={}", page.getTotalPages(), page.getTotalElements());
    }


}