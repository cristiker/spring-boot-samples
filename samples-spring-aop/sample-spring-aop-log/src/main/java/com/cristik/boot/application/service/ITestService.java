package com.cristik.boot.application.service;

import java.util.Map;

/**
 * @author cristik
 */
public interface ITestService {

    /**
     * 测试方法
     *
     * @param userName
     * @param score
     * @return
     */
    Map<String, Object> test(String userName, Integer score);
}
