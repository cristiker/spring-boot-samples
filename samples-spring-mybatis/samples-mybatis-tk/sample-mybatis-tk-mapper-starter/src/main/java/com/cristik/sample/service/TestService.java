package com.cristik.sample.service;

import com.cristik.sample.entity.po.Test;
import com.cristik.sample.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhenghua.ni
 */
@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

    public List<Test> queryList() {
        Test query = new Test();
        return testMapper.select(query);
    }

}