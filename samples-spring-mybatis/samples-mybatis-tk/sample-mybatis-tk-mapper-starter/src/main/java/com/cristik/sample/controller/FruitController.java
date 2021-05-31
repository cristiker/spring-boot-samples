package com.cristik.sample.controller;

import com.cristik.common.message.ResponseData;
import com.cristik.sample.entity.po.Fruit;
import com.cristik.sample.service.FruitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhenghua.ni
 */
@Validated
@RestController
@RequestMapping(value = "/api/v1")
public class FruitController {

    @Autowired
    private FruitService fruitService;

    @GetMapping(value = "/fruits", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<List<Fruit>> fruits() {
        return ResponseData.successData(fruitService.queryList());
    }

    @PostMapping(value = "/fruit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<List<Fruit>> fruit(@Valid @RequestBody Fruit fruit) {
        fruitService.insertFruit(fruit);
        return ResponseData.success();
    }

}