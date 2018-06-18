package com.wsmhz.web.shop.common.service;

import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.Category;

import java.util.List;
import java.util.Set;

/**
 * create by tangbj on 2018/5/19
 */
public interface CategoryService extends BaseService<Category> {

    PageInfo<Category> selectPageListByNameAndStatus(Integer pageNum, Integer pageSize, String name,Boolean status);

    List<Category> selectAllWithChildren(Long parentCategoryId);

    List<Category> selectByParentId(Long id);

    Set<Long> selectDeepChildIds(Set<Long> idSet,Long id);
}
