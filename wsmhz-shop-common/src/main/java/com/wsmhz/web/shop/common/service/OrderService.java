package com.wsmhz.web.shop.common.service;

import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.Order;

/**
 * create by tangbj on 2018/5/27
 */
public interface OrderService extends BaseService<Order>{

    ServerResponse createOrder(Long userId,Long shippingId);
}
