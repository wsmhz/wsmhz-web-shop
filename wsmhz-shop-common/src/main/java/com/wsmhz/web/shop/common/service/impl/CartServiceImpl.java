package com.wsmhz.web.shop.common.service.impl;

import com.wsmhz.security.core.service.BaseServiceImpl;
import com.wsmhz.web.shop.common.dao.CartMapper;
import com.wsmhz.web.shop.common.dao.ProductMapper;
import com.wsmhz.web.shop.common.domain.Cart;
import com.wsmhz.web.shop.common.domain.Product;
import com.wsmhz.web.shop.common.dto.CartProductDto;
import com.wsmhz.web.shop.common.service.CartService;
import com.wsmhz.web.shop.common.utils.BigDecimalUtil;
import com.wsmhz.web.shop.common.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * create by tangbj on 2018/5/26
 */
@Service
public class CartServiceImpl extends BaseServiceImpl<Cart> implements CartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public Integer insertCart(Cart cart) {
        Cart resultCart = selectByUserIdAndProductId(cart.getUserId(),cart.getProductId());
        Integer result = 0;
        if(resultCart != null){
            int quantity = resultCart.getQuantity() + cart.getQuantity();
            resultCart.setQuantity(quantity);
            result = cartMapper.updateByPrimaryKeySelective(resultCart);
        }else{
            result = cartMapper.insertSelective(cart);
        }
        return result;
    }

    @Override
    public Cart selectByUserIdAndProductId(Long userId, Long productId) {
        Example example = new Example(Cart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("productId",productId);
        Cart cart = cartMapper.selectOneByExample(example);
        return cart;
    }

    @Override
    public CartVo selectUserCart(Long userId, Boolean checked) {
        List<Cart> cartList = selectByUserId(userId,checked);
        CartVo cartVo = new CartVo();
        List<CartProductDto> cartProductDtoList = new ArrayList<>();
        BigDecimal cartTotalPrice = new BigDecimal("0");
        for (Cart cart : cartList) {
            Product product  = productMapper.selectByPrimaryKey(cart.getProductId());
            if(product !=null ){
                CartProductDto cartProductDto = new CartProductDto();
                cartProductDto.setId(cart.getId());
                cartProductDto.setProductChecked(cart.getChecked());
                cartProductDto.setProductId(product.getId());
                cartProductDto.setProductMainImage(product.getMainImage());
                cartProductDto.setProductName(product.getName());
                cartProductDto.setProductStatus(product.getStatus());
                cartProductDto.setProductStock(product.getStock());
                cartProductDto.setProductSubtitle(product.getSubtitle());
                //判断库存
                int buyLimitCount = 0;
                if(product.getStock() >= cart.getQuantity()){  //充足
                    buyLimitCount = cart.getQuantity();
                    cartProductDto.setStockEnough(true);
                }else{
                    buyLimitCount = product.getStock();
                    cartProductDto.setStockEnough(false);
                    Cart cartForQuantity = new Cart();
                    cartForQuantity.setId(cart.getId());
                    cartForQuantity.setQuantity(buyLimitCount);      //不足,并更新购物车数量
                    cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                }
                cartProductDto.setQuantity(buyLimitCount);
                cartProductDto.setUserId(userId);
                cartProductDto.setProductPrice(product.getPrice());
                BigDecimal productTotalPrice = BigDecimalUtil.mul(product.getPrice().doubleValue(),cart.getQuantity());
                cartProductDto.setProductTotalPrice(productTotalPrice);
                cartProductDtoList.add(cartProductDto);
                if(cart.getChecked()){
                    //如果已经勾选,增加到整个的购物车总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),productTotalPrice.doubleValue());
                }
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductList(cartProductDtoList);
        cartVo.setAllChecked(getAllCheckedStatus(userId));
        return cartVo;
    }

    @Override
    public List<Cart> selectByUserId(Long userId, Boolean checked) {
        Example example = new Example(Cart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        if(checked != null){
            criteria.andEqualTo("checked",checked);
        }
        List<Cart> cartList = cartMapper.selectByExample(example);
        return cartList;
    }

    private boolean getAllCheckedStatus(Long userId){
        if(userId == null){
            return false;
        }
        Example example = new Example(Cart.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("checked",false);
        return cartMapper.selectCountByExample(example) == 0;  //查询所有未选中状态条数为0，则为全选
    }
}
