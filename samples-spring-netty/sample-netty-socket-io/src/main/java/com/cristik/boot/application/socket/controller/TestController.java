package com.cristik.boot.application.socket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author cristik
 */

@Controller
@RequestMapping("/home")
public class TestController {
    @GetMapping("/test")
    public String index() {
        return "/socket/test";
    }

    @GetMapping("/test2")
    public String index2() {
        return "/socket/test_bak";
    }
}
