package com.wsmhz.web.shop.back.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.service.BaseServiceImpl;
import com.wsmhz.web.shop.back.dao.CategoryMapper;
import com.wsmhz.web.shop.back.domain.Category;
import com.wsmhz.web.shop.back.service.CategoryService;
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
}
