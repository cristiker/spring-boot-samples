package com.cristik.sample.jetty.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cristik
 */
@Controller
public class TestController {

    @GetMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "hello";
    }

}
