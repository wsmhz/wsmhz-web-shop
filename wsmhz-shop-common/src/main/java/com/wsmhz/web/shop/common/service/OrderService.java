package com.wsmhz.web.shop.common.service;

import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.Order;
import com.wsmhz.web.shop.common.domain.OrderItem;
import com.wsmhz.web.shop.common.enums.OrderConst;

import java.util.List;

/**
 * create by tangbj on 2018/5/27
 */
public interface OrderService extends BaseService<Order>{

    /**
     * @param userId 可为空
     * @param orderNo
     * @return
     */
    Order selectByUserIdAndOrderNo(Long userId,Long orderNo);

    List<OrderItem> selectByOrderItemNoAndUserId(Long orderNo, Long userId);

    ServerResponse createOrder(Long userId, Long shippingId);

    ServerResponse queryOrderPayStatus(Long userId,Long orderNo);

    ServerResponse cancel(Long userId,Long orderNo);

    ServerResponse selectOrderListByUserId(Integer pageNum, Integer pageSize, Long userId, Long orderNo, OrderConst.OrderStatusEnum status);

}
