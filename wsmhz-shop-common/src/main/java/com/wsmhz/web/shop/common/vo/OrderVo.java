package com.wsmhz.web.shop.common.vo;

import com.wsmhz.web.shop.common.dto.OrderItemDto;
import com.wsmhz.web.shop.common.dto.ShippingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * create by tangbj on 2018/5/27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVo {

    private Long id;

    private Long orderNo;
    /**
     * 支付金额
     */
    private BigDecimal payment;
    /**
     * 支付方式描述
     */
    private String paymentTypeDesc;
    /**
     * 邮费
     */
    private Integer postage;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 订单状态码
     */
    private Integer statusCode;
    /**
     * 订单状态描述
     */
    private String statusDesc;
    /**
     * 支付时间
     */
    private Date paymentTime;
    /**
     * 发货时间
     */
    private Date sendTime;
    /**
     * 交易完成时间
     */
    private Date endTime;
    /**
     * 交易关闭时间
     */
    private Date closeTime;
    /**
     * 订单创建时间
     */
    private Date createTime;
    /**
     * 订单的明细
     */
    private List<OrderItemDto> orderItemList;
    /**
     * 收货信息
     */
    private ShippingDto shipping;


}
