package com.cristik.sample.sharding.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cristik.sample.entity.sharding.po.SampleSharding;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhenghua.ni
 */
@Mapper
public interface SampleShardingMapper extends BaseMapper<SampleSharding> {
}
