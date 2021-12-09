package com.crsitik.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cristik.sample.entity.po.User;
import com.crsitik.mybatis.plus.mapper.UserMapper;
import com.crsitik.mybatis.plus.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author nizhenghua
 * @date 2021/12/08
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
