package com.cristik.boot.application.application.service.impl;

import com.cristik.boot.application.application.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cristik
 */
@Service
@CacheConfig(cacheNames = "users")
public class UserServiceImpl implements IUserService {

    public static final String USER_KEY_PREFIX = "USER_KEY";
    @Autowired
    CacheManager cacheManager;
    private Map<String, Object> users = new HashMap<>();

    @Override
    @Cacheable(value = "test", key = "{'" + USER_KEY_PREFIX + "',#userId}")
    public String getUserName(Integer userId) {
        return queryUser(userId);
    }

    @Override
    @CacheEvict(value = "test", allEntries = true)
    public void updateUserName(Integer userId, String userName) {
        users.put(userId + "", userName);
    }

    private String queryUser(Integer userId) {
        return (String) users.get(userId + "");
    }

    @PostConstruct
    public void init() {
        users.put("1", "张1");
        users.put("2", "张2");
        users.put("3", "张3");
        users.put("4", "张4");
        users.put("5", "张5");
    }

}
