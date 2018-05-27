package com.wsmhz.web.shop.common.service;

import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.Cart;
import com.wsmhz.web.shop.common.vo.CartVo;

import java.util.List;

/**
 * create by tangbj on 2018/5/26
 */
public interface CartService extends BaseService<Cart> {
    Integer insertCart(Cart cart);

    Cart selectByUserIdAndProductId(Long userId,Long productId);

    CartVo selectUserCart(Long userId, Boolean checked);

    List<Cart> selectByUserId(Long userId, Boolean checked);

}
