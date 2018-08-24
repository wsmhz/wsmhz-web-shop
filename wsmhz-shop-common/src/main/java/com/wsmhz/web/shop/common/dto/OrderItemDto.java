package com.wsmhz.web.shop.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * create by tangbj on 2018/5/27
 */
@Setter
@Getter
public class OrderItemDto {

    private Long orderNo;

    private Long productId;

    private String productName;

    private String productImage;
    /**
     * 生成订单时的商品单价
     */
    private BigDecimal currentUnitPrice;

    private Integer quantity;

    private BigDecimal totalPrice;

    private Date createTime;

}
