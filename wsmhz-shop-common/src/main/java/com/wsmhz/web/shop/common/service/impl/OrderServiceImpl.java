package com.wsmhz.web.shop.common.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.security.core.service.BaseServiceImpl;
import com.wsmhz.web.shop.common.dao.OrderItemMapper;
import com.wsmhz.web.shop.common.dao.OrderMapper;
import com.wsmhz.web.shop.common.dao.ProductMapper;
import com.wsmhz.web.shop.common.domain.*;
import com.wsmhz.web.shop.common.dto.OrderItemDto;
import com.wsmhz.web.shop.common.dto.ShippingDto;
import com.wsmhz.web.shop.common.enums.OrderConst;
import com.wsmhz.web.shop.common.enums.ProductConst;
import com.wsmhz.web.shop.common.service.CartService;
import com.wsmhz.web.shop.common.service.OrderService;
import com.wsmhz.web.shop.common.service.ShippingService;
import com.wsmhz.web.shop.common.utils.BigDecimalUtil;
import com.wsmhz.web.shop.common.vo.OrderVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.*;

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
    @Autowired
    private ShippingService shippingService;

    @Override
    public Order selectByUserIdAndOrderNo(Long userId, Long orderNo) {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        if(userId != null){
            criteria.andEqualTo("userId",userId);
        }
        criteria.andEqualTo("orderNo",orderNo);
        return orderMapper.selectOneByExample(example);
    }

    @Override
    public List<OrderItem> selectByOrderItemNoAndUserId(Long orderNo, Long userId) {
        Example example = new Example(OrderItem.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderNo",orderNo);
        if(userId != null){
            criteria.andEqualTo("userId",userId);
        }
        return orderItemMapper.selectByExample(example);
    }

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

    @Override
    public ServerResponse queryOrderPayStatus(Long userId, Long orderNo) {
        Order order = selectByUserIdAndOrderNo(userId,orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("用户没有该订单");
        }
        if(order.getStatus().getCode() >= OrderConst.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createBySuccess(order.getStatus().getCode());
        }
        return ServerResponse.createBySuccess();
    }

    @Override
    public ServerResponse cancel(Long userId,Long orderNo) {
        Order order  = selectByUserIdAndOrderNo(userId,orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("该用户此订单不存在");
        }
        if(order.getStatus().getCode() != OrderConst.OrderStatusEnum.NO_PAY.getCode()){
            return ServerResponse.createByErrorMessage("已付款,无法取消订单");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(OrderConst.OrderStatusEnum.CANCELED);
        int row = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if(row > 0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<PageInfo> selectOrderListByUserId(Integer pageNum, Integer pageSize,Long userId,Long orderNo,OrderConst.OrderStatusEnum status) {
        PageInfo<Order> pageInfo = getOrderListByUserId(pageNum,pageSize,userId,orderNo,status);
        List<OrderVo> orderVoList = assembleOrderVoList(pageInfo.getList(),userId);
        PageInfo<OrderVo> voPageInfo = new PageInfo<>();
        pageInfo.setList(null);
        BeanUtils.copyProperties(pageInfo,voPageInfo);
        voPageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(voPageInfo);
    }

    @Override
    public ServerResponse selectOrderDetail(Long userId, Long id) {
        Order order = selectByPrimaryKey(id);
        if(order != null){
            List<OrderItem> orderItemList = selectByOrderItemNoAndUserId(order.getOrderNo(),userId);
            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return  ServerResponse.createByErrorMessage("没有找到该订单");
    }

    @Override
    public ServerResponse shipment(Long orderNo) {
        Order order  = selectByPrimaryKey(orderNo);
        if(order == null){
            return ServerResponse.createByErrorMessage("此订单不存在");
        }
        if(order.getStatus().getCode() != OrderConst.OrderStatusEnum.PAID.getCode()){
            return ServerResponse.createByErrorMessage("未付款,无法发货");
        }
        Order updateOrder = new Order();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(OrderConst.OrderStatusEnum.SHIPPED);
        updateOrder.setSendTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if(row > 0){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public List<Order> selectByOrderStatusAndCreateDate(OrderConst.OrderStatusEnum status, Date createDate) {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",status);
        criteria.andLessThanOrEqualTo("createDate",createDate);
        return orderMapper.selectByExample(example);
    }

    @Override
    public List<Map<String, String>> selectMonthOrders(Integer month) {
        List<Map<String, String>> List = orderMapper.selectMonthOrders(month);
        for (Map<String, String> map : List) {
            Map<String, String> targetMap = new HashMap<>();
            Map.Entry<String,String> entry = map.entrySet().iterator().next(); //第一个元素,枚举替换
            OrderConst.OrderStatusEnum status = OrderConst.OrderStatusEnum.getItem(entry.getValue());
            if(status != null){
                map.put(entry.getKey(),status.getValue());
            }
        }
        return List;
    }

    private PageInfo<Order> getOrderListByUserId(Integer pageNum, Integer pageSize, Long userId, Long orderNo,OrderConst.OrderStatusEnum status) {
        PageHelper.startPage(pageNum, pageSize);
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        if(userId != null){
            criteria.andEqualTo("userId",userId);
        }
        if(orderNo != null){
            criteria.andEqualTo("orderNo",orderNo);
        }
        if(status != null){
            criteria.andEqualTo("status",status);
        }
        return new PageInfo<>(orderMapper.selectByExample(example));
    }

    private List<OrderVo> assembleOrderVoList(List<Order> orderList, Long userId){
        List<OrderVo> orderVoList = Lists.newArrayList();
        for(Order order : orderList){
            List<OrderItem>  orderItemList = selectByOrderItemNoAndUserId(order.getOrderNo(),userId);

            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    private OrderVo assembleOrderVo(Order order,List<OrderItem> orderItemList){
        OrderVo orderVo = new OrderVo();
        orderVo.setId(order.getId());
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentTypeDesc(order.getPaymentType().getValue());

        orderVo.setPostage(order.getPostage());
        orderVo.setStatusDesc(order.getStatus().getValue());

        Shipping shipping = shippingService.selectByPrimaryKey(order.getShippingId());
        if(shipping != null){
            orderVo.setShipping(assembleShippingVo(shipping));
        }

        orderVo.setPaymentTime(order.getPaymentTime());
        orderVo.setSendTime(order.getSendTime());
        orderVo.setEndTime(order.getEndTime());
        orderVo.setCreateTime(order.getCreateDate());
        orderVo.setCloseTime(order.getCloseTime());

        List<OrderItemDto> orderItemVoList = Lists.newArrayList();

        for(OrderItem orderItem : orderItemList){
            OrderItemDto orderItemDto = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemDto);
        }
        orderVo.setOrderItemList(orderItemVoList);
        return orderVo;
    }

    private OrderItemDto assembleOrderItemVo(OrderItem orderItem){
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setOrderNo(orderItem.getOrderNo());
        orderItemDto.setProductId(orderItem.getProductId());
        orderItemDto.setProductName(orderItem.getProductName());
        orderItemDto.setProductImage(orderItem.getProductImage());
        orderItemDto.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setTotalPrice(orderItem.getTotalPrice());

        orderItemDto.setCreateTime(orderItem.getCreateDate());
        return orderItemDto;
    }

    private ShippingDto assembleShippingVo(Shipping shipping){
        ShippingDto shippingDto = new ShippingDto();
        shippingDto.setReceiverName(shipping.getReceiverName());
        shippingDto.setReceiverAddress(shipping.getReceiverAddress());
        shippingDto.setReceiverProvince(shipping.getReceiverProvince());
        shippingDto.setReceiverCity(shipping.getReceiverCity());
        shippingDto.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingDto.setReceiverMobile(shipping.getReceiverMobile());
        shippingDto.setReceiverZip(shipping.getReceiverZip());
        shippingDto.setReceiverPhone(shippingDto.getReceiverPhone());
        return shippingDto;
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
