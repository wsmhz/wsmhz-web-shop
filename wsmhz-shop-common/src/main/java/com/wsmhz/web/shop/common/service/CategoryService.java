package com.wsmhz.web.shop.common.service;

import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.Category;

import java.util.List;

/**
 * create by tangbj on 2018/5/19
 */
public interface CategoryService extends BaseService<Category> {

    PageInfo<Category> selectPageListByNameAndStatus(Integer pageNum, Integer pageSize, String name,Boolean status);

    List<Category> selectAllWithChildren(Long parentCategoryId);

    List<Category> select(Long id);
}
