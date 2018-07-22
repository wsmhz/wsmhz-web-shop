package com.wsmhz.web.shop.front.web.controller;

import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.enums.ProductConst;
import com.wsmhz.web.shop.common.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by tangbj on 2018/5/19
 */
@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ServerResponse<Product> select(@PathVariable("id")Long id){
        return  ServerResponse.createBySuccess(productService.selectByPrimaryKey(id));
    }

    @PostMapping()
    public ServerResponse<PageInfo> selectAllOfPage(@RequestParam(value = "pageNum")Integer pageNum,
                                                    @RequestParam(value = "pageSize")Integer pageSize,
                                                    @RequestParam(value = "keyWord",required = false)String keyWord,
                                                    @RequestParam(value = "categoryId",required = false)Long categoryId,
                                                    @RequestParam(value = "flag",required = false)ProductConst.FlagEnum flag){
        PageInfo<Product> pageInfo = productService.selectPageListByNameAndCategoryId(pageNum,pageSize,keyWord,categoryId,ProductConst.StatusEnum.ON_SALE,flag);
        return  ServerResponse.createBySuccess(pageInfo);
    }


}
