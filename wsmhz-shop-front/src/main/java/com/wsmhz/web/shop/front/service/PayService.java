package com.wsmhz.web.shop.front.service;
import com.wsmhz.common.business.response.ServerResponse;
import com.wsmhz.common.business.service.BaseService;
import com.wsmhz.web.shop.common.domain.Order;

import java.util.Map;

/**
 * create by tangbj on 2018/5/29
 */
public interface PayService extends BaseService<Order> {
    /**
     * 支付订单
     * @param orderNo
     * @param userId
     * @param path 本地二维码路径（会删除）
     * @return
     */
    ServerResponse pay(Long orderNo, Long userId, String path);

    /**
     * 支付宝支付回调
     * @param params
     * @return
     */
    ServerResponse aliPayCallback(Map<String,String> params);

    /**
     * 关闭订单
     * @param hour
     */
    void closeOrder(int hour);
}
