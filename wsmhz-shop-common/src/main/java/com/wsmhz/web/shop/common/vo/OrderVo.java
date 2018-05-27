package com.wsmhz.web.shop.common.vo;

import com.wsmhz.web.shop.common.dto.OrderItemDto;
import com.wsmhz.web.shop.common.dto.ShippingDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * create by tangbj on 2018/5/27
 */
public class OrderVo {

    private Long orderNo;

    private BigDecimal payment;

    private String paymentTypeDesc;

    private Integer postage;

    private String statusDesc;

    private String paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    //订单的明细
    private List<OrderItemDto> orderItemList;

    private ShippingDto shipping;
}
