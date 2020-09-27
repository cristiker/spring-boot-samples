package com.cristik.samples.mapper.product;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cristik.samples.entity.po.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhenghua.ni
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
