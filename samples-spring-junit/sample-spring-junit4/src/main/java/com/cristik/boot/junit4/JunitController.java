package com.cristik.boot.junit4;

import com.cristik.sample.framework.response.message.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author nizhenghua
 * @date 2021/11/16
 */
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class JunitController {


    @GetMapping(value = "/test")
    public ResponseEntity<ResponseData<String>> test() {
        ResponseData<String> responseData = ResponseData.error("系统开小差了!");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<ResponseData<String>> response = new ResponseEntity(responseData, headers, HttpStatus.UNAUTHORIZED);
        return response;
    }

}
