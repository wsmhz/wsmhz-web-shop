package com.wsmhz.web.shop.common.dao;
import com.wsmhz.common.business.mapper.MyBaseMapper;
import com.wsmhz.web.shop.common.domain.Product;

/**
 * create by tangbj on 2018/5/18
 */
public interface ProductMapper extends MyBaseMapper<Product> {
    Integer selectStockByProductId(Long id);
}
