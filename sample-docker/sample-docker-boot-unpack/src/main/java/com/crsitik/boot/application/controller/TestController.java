package com.crsitik.boot.application.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cristik
 */

@RestController
public class TestController {

    @GetMapping(value = "/docker", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        return "hello docker";
    }

}
