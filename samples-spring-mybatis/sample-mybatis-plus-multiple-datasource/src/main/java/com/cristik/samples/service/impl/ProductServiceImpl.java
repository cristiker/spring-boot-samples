package com.cristik.samples.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cristik.samples.entity.po.Product;
import com.cristik.samples.mapper.product.ProductMapper;
import com.cristik.samples.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * @author zhenghua.ni
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
