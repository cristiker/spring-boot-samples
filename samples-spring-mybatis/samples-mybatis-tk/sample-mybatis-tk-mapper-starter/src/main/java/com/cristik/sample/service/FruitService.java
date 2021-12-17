package com.cristik.sample.service;

import com.cristik.utils.exception.BusinessException;
import com.cristik.utils.exception.RollbackBusinessException;
import com.cristik.sample.entity.po.Fruit;
import com.cristik.sample.mapper.FruitMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionHolder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Date;
import java.util.List;

/**
 * @author zhenghua.ni
 */
@Service
public class FruitService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private FruitMapper fruitMapper;

    public List<Fruit> queryList() {
        Fruit query = new Fruit();
        return fruitMapper.select(query);
    }

    @Transactional(rollbackFor = RollbackBusinessException.class, noRollbackFor = BusinessException.class)
    public void insertFruit(Fruit fruit) {
        fruit.setDeleted(false)
                .setCreateTime(new Date())
                .setUpdateTime(new Date());
        fruitMapper.insert(fruit);
    }

    public void saveBatch() {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        SqlSessionHolder sqlSessionHolder = (SqlSessionHolder) TransactionSynchronizationManager.getResource(sqlSessionFactory);
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        if (sqlSessionHolder != null) {
            SqlSession sqlSession = sqlSessionHolder.getSqlSession();
            //原生无法支持执行器切换，当存在批量操作时，会嵌套两个session的，优先commit上一个session
            //按道理来说，这里的值应该一直为false。
            sqlSession.commit(!transaction);
        }

    }

    public void batchSaveOrUpdate() {
    }
}