package com.wsmhz.web.shop.common.domain;
import com.wsmhz.common.business.domain.Domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * create by tangbj on 2018/5/27
 */
@Setter
@Getter
@Table(name = "shipping")
public class Shipping extends Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    /**
     * 收件人姓名
     */
    private String receiverName;
    /**
     * 固定电话
     */
    private String receiverPhone;
    /**
     * 移动电话
     */
    private String receiverMobile;
    /**
     * 省份
     */
    private String receiverProvince;
    /**
     * 城市
     */
    private String receiverCity;
    /**
     * 县区
     */
    private String receiverDistrict;
    /**
     * 详细地址
     */
    private String receiverAddress;
    /**
     * 邮编
     */
    private String receiverZip;

}
