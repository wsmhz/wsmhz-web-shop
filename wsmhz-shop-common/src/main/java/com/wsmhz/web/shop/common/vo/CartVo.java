package com.wsmhz.web.shop.common.vo;

import com.wsmhz.web.shop.common.dto.CartProductDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * create by tangbj on 2018/5/26
 */
@Setter
@Getter
public class CartVo {

    private List<CartProductDto> cartProductList;

    private BigDecimal cartTotalPrice;

    private Boolean allChecked;//是否已经都勾选

}
