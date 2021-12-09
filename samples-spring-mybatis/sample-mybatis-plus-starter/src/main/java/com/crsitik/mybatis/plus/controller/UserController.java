package com.crsitik.mybatis.plus.controller;

import com.crsitik.mybatis.plus.service.UserService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nizhenghua
 * @date 2021/12/08
 */
@RestController
@RequestMapping(value = "/api/v1/test", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/test")
    public String test() {

        userService.saveOrUpdateBatch(Lists.newArrayList());
        return null;
    }
}
