package com.wsmhz.web.shop.back.service;

import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.back.domain.Product;
import com.wsmhz.web.shop.back.enums.ProductConst;

/**
 * create by tangbj on 2018/5/19
 */
public interface ProductService extends BaseService<Product>{

    PageInfo<Product> selectPageListByNameAndcategoryId(Integer pageNum, Integer pageSize, String name, Integer categoryId, ProductConst.StatusEnum status, ProductConst.FlagEnum flag);
}
