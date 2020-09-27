package com.cristik.samples.controller;

import com.cristik.sample.framework.response.message.ResponseData;
import com.cristik.samples.entity.po.Product;
import com.cristik.samples.entity.po.User;
import com.cristik.samples.service.ProductService;
import com.cristik.samples.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhenghua.ni
 */
@RestController
@RequestMapping(value = "/api/v1/user")
public class TestController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/info")
    public ResponseData<User> queryUserInfo(Long userId) {
        User user = userService.getById(userId);
        return ResponseData.success(user);
    }

    @GetMapping(value = "/info")
    public ResponseData<Product> getProduct(Long productId) {
        Product product = productService.getById(productId);
        return ResponseData.success(product);
    }
}
