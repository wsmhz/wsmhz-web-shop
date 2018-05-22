package com.wsmhz.web.shop.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.service.BaseServiceImpl;
import com.wsmhz.web.shop.common.dao.ProductMapper;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.enums.ProductConst;
import com.wsmhz.web.shop.common.service.ProductService;
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
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageInfo<Product> selectPageListByNameAndcategoryId(Integer pageNum, Integer pageSize, String name, Integer categoryId, ProductConst.StatusEnum status, ProductConst.FlagEnum flag) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Product.class);
        example.setOrderByClause("update_date desc");
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(name)){
            criteria.orLike("name","%"+name+"%").orLike("subtitle","%"+name+"%");
        }
        if( ! Objects.isNull(categoryId)){
            criteria.andEqualTo("categoryId",categoryId);
        }
        if( ! Objects.isNull(status)){
            criteria.andEqualTo("status",status);
        }
        if( ! Objects.isNull(status)){
            criteria.andEqualTo("flag",flag);
        }
        List<Product> list = productMapper.selectByExample(example);
        return new PageInfo<>(list);
    }
}
