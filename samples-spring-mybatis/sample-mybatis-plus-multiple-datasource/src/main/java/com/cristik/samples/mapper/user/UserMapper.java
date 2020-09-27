package com.cristik.samples.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cristik.samples.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhenghua.ni
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
