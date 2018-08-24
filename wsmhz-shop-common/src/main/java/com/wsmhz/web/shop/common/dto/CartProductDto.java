package com.wsmhz.web.shop.common.dto;

import com.wsmhz.web.shop.common.enums.ProductConst;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * create by tangbj on 2018/5/26
 */
@Setter
@Getter
public class CartProductDto {

    private Long id;

    private Long userId;

    private Long productId;

    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private ProductConst.StatusEnum productStatus;

    private BigDecimal productTotalPrice;

    private Integer productStock;

    private Boolean productChecked;

    private Boolean stockEnough; //库存是否充足

}
