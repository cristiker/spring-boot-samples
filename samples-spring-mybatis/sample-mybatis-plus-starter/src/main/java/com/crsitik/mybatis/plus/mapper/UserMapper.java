package com.crsitik.mybatis.plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cristik.sample.entity.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cristik
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 批量插入
     * @param users
     * @return
     */
    int batchSave(@Param("list") List<User> users);

}
