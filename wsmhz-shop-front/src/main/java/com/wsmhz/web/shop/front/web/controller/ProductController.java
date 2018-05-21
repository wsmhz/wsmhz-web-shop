package com.wsmhz.web.shop.front.web.controller;

import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by tangbj on 2018/5/19
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ServerResponse<Product> select(@PathVariable("id")Long id){
        return  ServerResponse.createBySuccess(productService.selectByPrimaryKey(id));
    }


}
