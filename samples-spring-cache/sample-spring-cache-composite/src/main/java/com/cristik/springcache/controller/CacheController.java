package com.cristik.caffeine.controller;

import com.cristik.caffeine.service.IUserService;
import com.cristik.common.utils.MessageUtil;
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
    public String testCache(Integer userId) {
        String user = userService.getUserName(userId);
        return MessageUtil.success("userName", (Object) user);
    }

    @GetMapping("/update")
    public String updateUserName(Integer userId, String userName) {
        userService.updateUserName(userId, userName);
        return MessageUtil.success();
    }
}