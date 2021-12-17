package com.cristik.springcache.controller;

import com.cristik.springcache.service.IUserService;
import com.cristik.utils.message.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cristik
 */

@RestController
@RequestMapping(value = "/cache")
public class CacheController {

    @Autowired
    IUserService userService;

    @GetMapping("/query")
    public ResponseData<String> testCache(Integer userId) {
        String user = userService.getUserName(userId);
        return ResponseData.successData(user);
    }

    @GetMapping("/update")
    public ResponseData updateUserName(Integer userId, String userName) {
        userService.updateUserName(userId, userName);
        return ResponseData.success();
    }
}