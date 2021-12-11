package com.cristik.mybatis.plus.test;

import com.cristik.sample.entity.po.User;
import com.crsitik.mybatis.plus.MybatisPlusApplication;
import com.crsitik.mybatis.plus.mapper.UserMapper;
import com.crsitik.mybatis.plus.service.UserService;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author cristik
 * @date 2021/12/09
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MybatisPlusApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class MybatisPlusSaveBatchTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    private List<User> users = Lists.newArrayList();

    @AfterEach
    public void clearUserTable() {
        log.info("step finish");
    }

    @BeforeEach
    public void prepareUsersToSave() {
        int total = 100000;
        for (int i = 0; i < total; i++) {
            User user = new User();
            user.setAvatar("")
                    .setBirthday(new Date())
                    .setEmail("test@qq.com")
                    .setEmailStatus(1)
                    .setFirstName("zhang")
                    .setLastName("fei")
                    .setIdCard("321281199211144581")
                    .setLocked(0)
                    .setNickName("crisitk")
                    .setUserName("user" + i)
                    .setPhone("18936762388")
                    .setSex(0)
                    .setRealName("张飞")
                    .setUid(UUID.randomUUID().toString())
                    .setTenantId(1L)
                    .setType(1)
                    .setPhoneStatus(1);
            users.add(user);
        }
    }

    // 无 2M4.092833307S
    // 有 8.744219998S
    @Test
    public void testMybatisPlusSaveBatch() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("批量插入开始{}条", users.size());
        userService.saveBatch(users);
        Duration duration = stopwatch.elapsed();
        log.info("耗时{}", duration);
    }

    // 51.825867912S
    @Test
    public void testInsertOneByOne() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("批量插入开始{}条", users.size());
        for (User user : users) {
            userService.save(user);
        }
        Duration duration = stopwatch.elapsed();
        log.info("耗时{}", duration);
    }

    // 6.104865237S
    @Test
    public void testMybatisInsertForeach() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("批量插入开始{}条", users.size());
        userMapper.batchSave(users);
        Duration duration = stopwatch.elapsed();
        log.info("耗时{}", duration);
    }

    // 9.731381502S
    @Test
    public void testBatchInsert() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("批量插入开始{}条", users.size());
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        int i = 1;
        for (User user : users) {
            userMapper.insert(user);
            if (i % 1000 == 0) {
                sqlSession.flushStatements();
            }
            i++;
        }
        sqlSession.commit();
        Duration duration = stopwatch.elapsed();
        log.info("耗时{}", duration);
    }



}
