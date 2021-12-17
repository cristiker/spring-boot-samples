package com.cristik.aop.log.controller;

import com.cristik.aop.log.aspect.IgnoreLog;
import com.cristik.aop.log.entity.User;
import com.cristik.aop.log.service.ITestService;
import com.cristik.utils.message.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author cristik
 */

@RestController
public class TestController {

    @Autowired
    ITestService testService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * AOP匹配方法
     *
     * @param userName
     * @param score
     * @return
     */
    @GetMapping("/test")
    public ResponseData<Map<String, Object>> test(String userName, Integer score) {
        return ResponseData.successData(testService.test(userName, score));
    }

    /**
     * AOP忽略特定注解
     *
     * @param userName
     * @param score
     * @return
     */
    @IgnoreLog
    @GetMapping("/test/ignore")
    public ResponseData<Map<String, Object>> ignoreLog(String userName, Integer score) {
        return ResponseData.successData(testService.test(userName, score));
    }

    /**
     * AOP匹配bindRequest
     *
     * @param user
     * @param bindingResult
     * @return
     */
    @PostMapping("/test/ignore")
    public ResponseData<Map<String, Object>> testBindRequest(@RequestBody User user, BindingResult bindingResult) {
        return ResponseData.successData(testService.test(user.getUserName(), user.getScore()));
    }

}