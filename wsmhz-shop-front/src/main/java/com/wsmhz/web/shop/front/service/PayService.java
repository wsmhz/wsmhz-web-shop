package com.wsmhz.web.shop.front.service;

import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.Order;

import java.util.Map;

/**
 * create by tangbj on 2018/5/29
 */
public interface PayService extends BaseService<Order>{

    ServerResponse pay(Long orderNo, Long userId, String path);

    ServerResponse aliPayCallback(Map<String,String> params);
}
