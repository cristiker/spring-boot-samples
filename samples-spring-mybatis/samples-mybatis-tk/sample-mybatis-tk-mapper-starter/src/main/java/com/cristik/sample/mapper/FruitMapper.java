package com.cristik.sample.mapper;

import com.cristik.sample.entity.po.Fruit;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author zhenghua.ni
 */
@Mapper
public interface FruitMapper extends BaseMapper<Fruit> {
}