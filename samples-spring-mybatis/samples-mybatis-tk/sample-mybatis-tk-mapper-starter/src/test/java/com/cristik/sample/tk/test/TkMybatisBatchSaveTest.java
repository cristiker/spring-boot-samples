package com.cristik.sample.tk.test;

import com.cristik.sample.TkMybatisApplication;
import com.cristik.sample.entity.po.Fruit;
import com.cristik.sample.mapper.FruitMapper;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
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

/**
 * @author nizhenghua
 * @date 2021/12/10
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TkMybatisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class TkMybatisBatchSaveTest {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private FruitMapper fruitMapper;

    private List<Fruit> fruits = Lists.newArrayList();

    @BeforeEach
    public void beforeSave() {
        int total = 10000;
        for (int i = 0; i < total; i++) {
            Fruit fruit = new Fruit();
            fruit.setFruitName("xxxxx");
            fruit.setCreateTime(new Date());
            fruit.setUpdateTime(new Date());
            fruit.setDeleted(false);
            fruits.add(fruit);
        }
    }

    // 无 41.974416941S
    // 有 xxxx

    /**
     * {@link 'https://blog.csdn.net/qq_15003505/article/details/108737403'}
     */
    @Test
    public void tkMybatisBatchSave() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("批量插入开始{}条", fruits.size());
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        FruitMapper fruitMapper = sqlSession.getMapper(FruitMapper.class);
        int i = 1;
        for (Fruit fruit : fruits) {
            fruitMapper.insert(fruit);
            if (i % 1000 == 0) {
                sqlSession.flushStatements();
            }
            i++;
        }
        sqlSession.commit();
        sqlSession.clearCache();
        Duration duration = stopwatch.elapsed();
        log.info("耗时{}", duration);
    }

    /**
     * 自定义保存
     */
    @Test
    public void tkMybatisBatchInsert() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("批量插入开始{}条", fruits.size());
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        FruitMapper fruitMapper = sqlSession.getMapper(FruitMapper.class);
        int i = 1;
        for (Fruit fruit : fruits) {
            fruitMapper.save(fruit);
            if (i % 1000 == 0) {
                sqlSession.flushStatements();
            }
            i++;
        }
        sqlSession.commit();
        sqlSession.clearCache();
        Duration duration = stopwatch.elapsed();
        log.info("耗时{}", duration);
    }

    // 1.520291841S
    @Test
    public void tkMybatisForeachSave() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        log.info("批量插入开始{}条", fruits.size());
        fruitMapper.batchSave(fruits);
        Duration duration = stopwatch.elapsed();
        log.info("耗时{}", duration);
    }

}
