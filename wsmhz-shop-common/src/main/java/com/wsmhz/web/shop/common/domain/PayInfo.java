package com.wsmhz.web.shop.common.domain;

import com.wsmhz.security.core.domain.common.Domain;
import com.wsmhz.web.shop.common.enums.OrderConst;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * create by tangbj on 2018/5/30
 */
@Table(name = "payInfo")
public class PayInfo extends Domain{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long orderNo;
    /**
     * 支付平台
     */
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private OrderConst.PayPlatformEnum payPlatform;
    /**
     * 流水号
     */
    private String platformNumber;
    /**
     * 平台订单状态
     */
    private String platformStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public OrderConst.PayPlatformEnum getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(OrderConst.PayPlatformEnum payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getPlatformNumber() {
        return platformNumber;
    }

    public void setPlatformNumber(String platformNumber) {
        this.platformNumber = platformNumber;
    }

    public String getPlatformStatus() {
        return platformStatus;
    }

    public void setPlatformStatus(String platformStatus) {
        this.platformStatus = platformStatus;
    }
}
