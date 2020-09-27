package com.cristik.boot.framework.application.service.impl;

import com.cristik.boot.framework.application.dao.UserMapper;
import com.cristik.boot.framework.application.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cristik
 */

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

}
