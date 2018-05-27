package com.wsmhz.web.shop.common.domain;

import com.wsmhz.security.core.domain.common.Domain;
import com.wsmhz.web.shop.common.enums.OrderConst;
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
@Table(name = "shop_order")
public class Order extends Domain{

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getShippingId() {
        return shippingId;
    }

    public void setShippingId(Long shippingId) {
        this.shippingId = shippingId;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public OrderConst.PaymentTypeEnum getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(OrderConst.PaymentTypeEnum paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public OrderConst.OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderConst.OrderStatusEnum status) {
        this.status = status;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }
}
