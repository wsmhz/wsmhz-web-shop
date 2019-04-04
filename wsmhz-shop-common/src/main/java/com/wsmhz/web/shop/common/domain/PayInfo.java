package com.wsmhz.web.shop.common.domain;
import com.wsmhz.common.business.domain.Domain;
import com.wsmhz.web.shop.common.enums.OrderConst;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * create by tangbj on 2018/5/30
 */
@Setter
@Getter
@Table(name = "pay_info")
public class PayInfo extends Domain {

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

}
