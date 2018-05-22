package com.wsmhz.web.shop.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.service.BaseServiceImpl;
import com.wsmhz.web.shop.common.dao.CategoryMapper;
import com.wsmhz.web.shop.common.domain.Category;
import com.wsmhz.web.shop.common.service.CategoryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * create by tangbj on 2018/5/19
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageInfo<Category> selectPageListByNameAndStatus(Integer pageNum, Integer pageSize, String name, Boolean status) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Category.class);
        example.setOrderByClause("update_date desc");
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(name)){
            criteria.orLike("name","%"+name+"%");
        }
        if( ! Objects.isNull(status)){
            criteria.andEqualTo("status",status);
        }
        List<Category> list = categoryMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

    @Override
    public List<Category> selectAllWithChildren(Integer parentCategoryId) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",parentCategoryId);
        List<Category> categoryList = categoryMapper.selectByExample(example);// 所有父级
        deepSelectByParent(categoryList);
        return categoryList;
    }

    private void deepSelectByParent(List<Category> list){
        for (Category category : list) {
            Example example = new Example(Category.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("parentId",category.getId());
            List<Category> childCategoryList = categoryMapper.selectByExample(example);
            if( ! childCategoryList.isEmpty()){
                category.setChildren(childCategoryList);
            }
            deepSelectByParent(childCategoryList);
        }
    }
}
