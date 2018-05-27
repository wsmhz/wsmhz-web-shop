package com.wsmhz.web.shop.common.vo;

import com.wsmhz.web.shop.common.dto.CartProductDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * create by tangbj on 2018/5/26
 */
public class CartVo {

    private List<CartProductDto> cartProductList;

    private BigDecimal cartTotalPrice;

    private Boolean allChecked;//是否已经都勾选

    public List<CartProductDto> getCartProductList() {
        return cartProductList;
    }

    public void setCartProductList(List<CartProductDto> cartProductList) {
        this.cartProductList = cartProductList;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(Boolean allChecked) {
        this.allChecked = allChecked;
    }
}
