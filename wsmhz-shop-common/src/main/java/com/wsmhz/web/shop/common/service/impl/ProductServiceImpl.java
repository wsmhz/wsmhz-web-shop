package com.wsmhz.web.shop.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsmhz.common.business.service.BaseServiceImpl;
import com.wsmhz.web.shop.common.dao.ProductMapper;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.enums.ProductConst;
import com.wsmhz.web.shop.common.service.CategoryService;
import com.wsmhz.web.shop.common.service.ProductService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * create by tangbj on 2018/5/19
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CategoryService categoryService;

    @Override
    public PageInfo<Product> selectPageListByNameAndCategoryId(Integer pageNum, Integer pageSize, String name, Long categoryId, ProductConst.StatusEnum status, ProductConst.FlagEnum flag) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Product.class);
        example.setOrderByClause("update_date desc");
        Example.Criteria criteria = example.createCriteria();
        if( ! Objects.isNull(status)){
            criteria.andEqualTo("status",status);
        }
        if(StringUtils.isNotBlank(name)) {
            criteria.andCondition("(name like '%"+name+"%' or subtitle like '%"+name+"%')");
        }
        if( ! Objects.isNull(categoryId)){
//            criteria.andEqualTo("categoryId",categoryId);
            criteria.andIn("categoryId",categoryService.selectDeepChildIds(new HashSet<>(),categoryId));
        }
        if( ! Objects.isNull(flag)){
            criteria.andEqualTo("flag",flag);
        }
        List<Product> list = productMapper.selectByExample(example);
        return new PageInfo<>(list);
    }

//    private List<Product> deepSelectByCategoryId(List<Product> list,Integer categoryId){
//        Example example = new Example(Product.class);
//        Example.Criteria criteria = example.createCriteria();
//
//    }
}
