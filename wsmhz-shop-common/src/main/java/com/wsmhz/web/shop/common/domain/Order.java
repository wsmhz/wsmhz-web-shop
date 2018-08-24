package com.wsmhz.web.shop.common.domain;

import com.wsmhz.security.data.Domain;
import com.wsmhz.web.shop.common.enums.OrderConst;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * create by tangbj on 2018/5/27
 */
@Setter
@Getter
@Table(name = "shop_order")
public class Order extends Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 订单号
     */
    private Long orderNo;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 收货地址id
     */
    private Long shippingId;
    /**
     * 支付金额
     */
    private BigDecimal payment;
    /**
     * 支付方式
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private OrderConst.PaymentTypeEnum paymentType;
    /**
     * 邮费
     */
    private Integer postage;
    /**
     * 订单状态
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private OrderConst.OrderStatusEnum status;
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

}
