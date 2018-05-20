package com.wsmhz.web.shop.back.service;

import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.back.domain.Category;

/**
 * create by tangbj on 2018/5/19
 */
public interface CategoryService extends BaseService<Category> {

    PageInfo<Category> selectPageListByNameAndStatus(Integer pageNum, Integer pageSize, String name,Boolean status);
}
