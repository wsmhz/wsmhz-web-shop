package com.wsmhz.web.shop.common.service;

import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.enums.ProductConst;

/**
 * create by tangbj on 2018/5/19
 */
public interface ProductService extends BaseService<Product>{

    PageInfo<Product> selectPageListByNameAndCategoryId(Integer pageNum, Integer pageSize, String name, Long categoryId, ProductConst.StatusEnum status, ProductConst.FlagEnum flag);
}
