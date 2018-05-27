package com.wsmhz.web.shop.common.service.impl;

import com.google.common.collect.Lists;
import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.security.core.service.BaseServiceImpl;
import com.wsmhz.web.shop.common.dao.OrderItemMapper;
import com.wsmhz.web.shop.common.dao.OrderMapper;
import com.wsmhz.web.shop.common.dao.ProductMapper;
import com.wsmhz.web.shop.common.domain.Cart;
import com.wsmhz.web.shop.common.domain.Order;
import com.wsmhz.web.shop.common.domain.OrderItem;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.enums.OrderConst;
import com.wsmhz.web.shop.common.enums.ProductConst;
import com.wsmhz.web.shop.common.service.CartService;
import com.wsmhz.web.shop.common.service.OrderService;
import com.wsmhz.web.shop.common.utils.BigDecimalUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * create by tangbj on 2018/5/27
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService{

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartService cartService;

    @Transactional
    @Override
    public ServerResponse createOrder(Long userId, Long shippingId) {
        //从购物车中获取数据
        List<Cart> cartList = cartService.selectByUserId(userId,true);
        //计算这个订单的总价
        ServerResponse serverResponse = this.getCartOrderItem(userId,cartList);
        if(!serverResponse.isSuccess()){
            return serverResponse;
        }
        List<OrderItem> orderItemList = (List<OrderItem>)serverResponse.getData();
        BigDecimal payment = this.getOrderTotalPrice(orderItemList);
        //生成订单
        Order order = this.assembleOrder(userId,shippingId,payment);
        if(order == null){
            return ServerResponse.createByErrorMessage("生成订单错误");
        }
        for(OrderItem orderItem : orderItemList){
            orderItem.setOrderNo(order.getOrderNo());
            orderItemMapper.insertSelective(orderItem); //插入订单子表数据
        }
        //减少产品的库存
        this.reduceProductStock(orderItemList);
        //清空购物车
        this.cleanCart(cartList);
        return ServerResponse.createBySuccess(order.getOrderNo());
    }

    private ServerResponse<List<OrderItem>> getCartOrderItem(Long userId,List<Cart> cartList){
        List<OrderItem> orderItemList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(cartList)){
            return ServerResponse.createByErrorMessage("购物车为空");
        }
        //校验购物车的数据,包括产品的状态和数量
        for(Cart cartItem : cartList){
            OrderItem orderItem = new OrderItem();
            Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
            if( ! ProductConst.StatusEnum.ON_SALE.equals(product.getStatus())){
                return ServerResponse.createByErrorMessage("产品："+product.getName()+"不是在线售卖状态");
            }
            //校验库存
            if(cartItem.getQuantity() > product.getStock()){
                return ServerResponse.createByErrorMessage("产品："+product.getName()+"库存不足");
            }
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartItem.getQuantity()));
            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItemList);
    }

    private BigDecimal getOrderTotalPrice(List<OrderItem> orderItemList){
        BigDecimal payment = new BigDecimal("0");
        for(OrderItem orderItem : orderItemList){
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }
        return payment;
    }

    private Order assembleOrder(Long userId,Long shippingId,BigDecimal payment){
        Order order = new Order();
        long orderNo = this.generateOrderNo();
        order.setOrderNo(orderNo);
        order.setStatus(OrderConst.OrderStatusEnum.NO_PAY);
        order.setPostage(0);
        order.setPaymentType(OrderConst.PaymentTypeEnum.ONLINE_PAY);
        order.setPayment(payment);

        order.setUserId(userId);
        order.setShippingId(shippingId);
        int rowCount = orderMapper.insertSelective(order);
        if(rowCount > 0){
            return order;
        }
        return null;
    }

    private long generateOrderNo(){
        long currentTime =System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);
    }

    private void cleanCart(List<Cart> cartList){
        for(Cart cart : cartList){
            cartService.deleteByPrimaryKey(cart.getId());
        }
    }

    private void reduceProductStock(List<OrderItem> orderItemList){
        for(OrderItem orderItem : orderItemList){
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock()-orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective(product);
        }
    }


}
