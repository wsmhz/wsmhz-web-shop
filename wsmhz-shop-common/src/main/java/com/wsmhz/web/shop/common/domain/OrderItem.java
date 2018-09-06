package com.wsmhz.web.shop.common.domain;

import com.wsmhz.common.data.domain.Domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * create by tangbj on 2018/5/27
 * 订单的明细
 */
@Setter
@Getter
@Table(name = "order_item")
public class OrderItem extends Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    /**
     * 订单号
     */
    private Long orderNo;
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品主图
     */
    private String productImage;
    /**
     * 生成订单时的商品单价
     */
    private BigDecimal currentUnitPrice;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 订单总价
     */
    private BigDecimal totalPrice;

}
