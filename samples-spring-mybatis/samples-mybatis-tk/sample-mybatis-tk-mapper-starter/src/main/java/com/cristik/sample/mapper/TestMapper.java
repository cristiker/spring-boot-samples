package com.cristik.sample.mapper;

import com.cristik.sample.entity.po.Test;
import org.apache.ibatis.annotations.Mapper;
import tk.mybatis.mapper.common.BaseMapper;

/**
 * @author zhenghua.ni
 */
@Mapper
public interface TestMapper extends BaseMapper<Test> {
}