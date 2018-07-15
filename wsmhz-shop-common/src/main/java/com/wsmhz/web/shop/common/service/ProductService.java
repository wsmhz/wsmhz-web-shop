package com.wsmhz.web.shop.common.service;

import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.enums.ProductConst;

/**
 * create by tangbj on 2018/5/19
 */
public interface ProductService extends BaseService<Product>{
    /**
     * 商品列表 （带搜索）
     * @param pageNum
     * @param pageSize
     * @param name 商品名称 可为空
     * @param categoryId 分类ID 可为空
     * @param status 状态 可为空
     * @param flag 标识 可为空
     * @return
     */
    PageInfo<Product> selectPageListByNameAndCategoryId(Integer pageNum, Integer pageSize, String name, Long categoryId, ProductConst.StatusEnum status, ProductConst.FlagEnum flag);

}
