package com.cristik.boot.application.controller;

import com.cristik.aop.log.service.ICalculatorService;
import com.cristik.utils.message.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cristik
 */

@RestController
public class CacheController {

    @Autowired
    ICalculatorService calculatorService;

    @RequestMapping(value = "/test/cache", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData calculator(@RequestBody List<Long> numbers) {
        calculatorService.factorials(numbers);
        return ResponseData.success();
    }

    @RequestMapping(value = "/test/cache/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String calculator(@PathVariable("number") Long number) {
        return calculatorService.factorial(number) + "";
    }

}