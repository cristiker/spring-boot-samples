package com.cristik.sample.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenghua.ni
 */
@Api(value = "swaggerDemo")
@RestController
@RequestMapping("/api/v1/swagger")
public class SwaggerDemoController {

    @ApiOperation(value = "test")
    @GetMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        return "";
    }

}
