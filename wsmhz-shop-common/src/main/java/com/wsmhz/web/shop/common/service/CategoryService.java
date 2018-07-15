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
    /**
     * 分类列表 （带搜索）
     * @param pageNum
     * @param pageSize
     * @param name 可为空
     * @param status 可为空
     * @return
     */
    PageInfo<Category> selectPageListByNameAndStatus(Integer pageNum, Integer pageSize, String name,Boolean status);

    /**
     * 根据父分类递归查询其所有子分类
     * @param parentCategoryId
     * @return
     */
    List<Category> selectAllWithChildren(Long parentCategoryId);

    /**
     * 根据父分类查询其一级子分类
     * @param id
     * @return
     */
    List<Category> selectByParentId(Long id);

    /**
     * 递归查询所有子分类ID集合
     * @param idSet
     * @param id
     * @return
     */
    Set<Long> selectDeepChildIds(Set<Long> idSet,Long id);
}
