package com.cristik.aop.log.service.impl;

import com.cristik.aop.log.service.ITestService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author cristik
 */

@Service
public class TestServiceImpl implements ITestService {

    @Override
    public Map<String, Object> test(String userName, Integer score) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("userName", userName);
        data.put("score", score);
        return data;
    }

}