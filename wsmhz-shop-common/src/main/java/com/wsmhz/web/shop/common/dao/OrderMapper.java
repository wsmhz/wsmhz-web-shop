package com.wsmhz.web.shop.common.dao;

import com.wsmhz.common.business.mapper.MyBaseMapper;
import com.wsmhz.web.shop.common.domain.Order;

import java.util.List;
import java.util.Map;

/**
 * create by tangbj on 2018/5/27
 */
public interface OrderMapper extends MyBaseMapper<Order> {
    List<Map<String,String>> selectMonthOrders(Integer month);
}
