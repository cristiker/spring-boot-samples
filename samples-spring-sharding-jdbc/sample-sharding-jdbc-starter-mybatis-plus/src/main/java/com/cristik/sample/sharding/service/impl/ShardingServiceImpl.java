package com.cristik.sample.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cristik.sample.entity.sharding.po.SampleSharding;
import com.cristik.sample.sharding.mapper.SampleShardingMapper;
import com.cristik.sample.sharding.service.ShardingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhenghua.ni
 */
@Slf4j
@Service
public class ShardingServiceImpl extends ServiceImpl<SampleShardingMapper, SampleSharding> implements ShardingService {

}
