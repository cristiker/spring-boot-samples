package com.cristik.sample.service;

import com.cristik.utils.exception.BusinessException;
import com.cristik.utils.exception.RollbackBusinessException;
import com.cristik.sample.entity.po.Fruit;
import com.cristik.sample.mapper.FruitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhenghua.ni
 */
@Service
public class FruitService {

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
}