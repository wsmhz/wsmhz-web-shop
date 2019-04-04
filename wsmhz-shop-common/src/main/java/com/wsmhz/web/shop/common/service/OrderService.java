package com.wsmhz.web.shop.common.service;
import com.wsmhz.common.business.service.BaseService;
import com.wsmhz.common.business.response.ServerResponse;
import com.wsmhz.web.shop.common.domain.Order;
import com.wsmhz.web.shop.common.domain.OrderItem;
import com.wsmhz.web.shop.common.enums.OrderConst;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * create by tangbj on 2018/5/27
 */
public interface OrderService extends BaseService<Order> {

    /**
     * 通过订单号查询订单
     * @param userId 可为空
     * @param orderNo
     * @return
     */
    Order selectByUserIdAndOrderNo(Long userId,Long orderNo);

    /**
     * 查询订单明细
     * @param orderNo
     * @param userId 可为空
     * @return
     */
    List<OrderItem> selectByOrderItemNoAndUserId(Long orderNo, Long userId);

    /**
     * 创建订单
     * @param userId
     * @param shippingId
     * @return
     */
    ServerResponse createOrder(Long userId, Long shippingId, String messageKey);

    /**
     * 查询创建订单是否成功
     * @param queryKey 查询的 key
     * @return
     */
    ServerResponse queryCreateOrder(String queryKey);

    /**
     * 查询该订单是否已支付
     * @param userId 可为空
     * @param orderNo
     * @return
     */
    ServerResponse queryOrderPayStatus(Long userId,Long orderNo);

    /**
     * 取消订单
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse cancel(Long userId,Long orderNo);

    /**
     * 订单列表 （带搜索）
     * @param pageNum
     * @param pageSize
     * @param userId 可为空
     * @param orderNo 可为空 用于搜索
     * @param status
     * @return
     */
    ServerResponse selectOrderListByUserId(Integer pageNum, Integer pageSize, Long userId, Long orderNo, OrderConst.OrderStatusEnum status);

    /**
     * 查询订单详情
     * @param userId 可为空
     * @param id 主键
     * @return
     */
    ServerResponse selectOrderDetail(Long userId, Long id);

    /**
     * 订单发货
     * @param id
     * @return
     */
    ServerResponse shipment(Long id);

    /**
     * 根据订单状态和创建时间查询
     * @param status
     * @param createDate 小于创建时间
     * @return
     */
    List<Order> selectByOrderStatusAndCreateDate(OrderConst.OrderStatusEnum status, Date createDate);

    /**
     * 查询各个状态下的订单数量
     * @param month 月数（如：1，当前时间的前一个月）
     * @return
     */
    List<Map<String,String>> selectMonthOrders(Integer month);

}
