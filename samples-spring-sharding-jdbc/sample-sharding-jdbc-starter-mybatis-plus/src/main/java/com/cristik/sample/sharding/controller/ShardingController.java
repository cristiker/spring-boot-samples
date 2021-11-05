package com.cristik.sample.sharding.controller;

import com.cristik.utils.lang.BeanUtil;
import com.cristik.sample.entity.sharding.po.SampleSharding;
import com.cristik.sample.entity.sharding.validator.SampleShardingValidator;
import com.cristik.sample.framework.response.message.ResponseData;
import com.cristik.sample.sharding.service.ShardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zhenghua.ni
 */
@Validated
@RestController
@RequestMapping("/api/v1/sharding")
public class ShardingController {

    @Autowired
    private ShardingService shardingService;

    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseData<Long> addShardingRecord(@Validated @RequestBody SampleShardingValidator sampleShardingValidator) {
        SampleSharding sampleSharding = BeanUtil.copyProperties(sampleShardingValidator, SampleSharding.class);
        shardingService.save(sampleSharding);
        return ResponseData.success(sampleSharding.getId());
    }

}
