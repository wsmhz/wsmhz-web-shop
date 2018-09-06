package com.wsmhz.web.shop.common.domain;
import com.wsmhz.common.data.domain.Domain;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * create by tangbj on 2018/5/18
 */
@Setter
@Getter
@Table(name = "cart")
public class Cart extends Domain {
    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户Id
     */
    private Long userId;
    /**
     * 产品Id
     */
    private Long productId;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 勾选状态
     */
    private Boolean checked;

}
