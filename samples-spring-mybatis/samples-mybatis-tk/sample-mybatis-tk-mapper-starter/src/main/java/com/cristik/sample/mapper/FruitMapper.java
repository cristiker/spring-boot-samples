package com.cristik.sample.mapper;

import com.cristik.sample.entity.po.Fruit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author zhenghua.ni
 */
@Mapper
public interface FruitMapper extends BaseMapper<Fruit> {

    int batchSave(@Param("list") List<Fruit> fruits);

    int save(@Param("item") Fruit fruit);

}