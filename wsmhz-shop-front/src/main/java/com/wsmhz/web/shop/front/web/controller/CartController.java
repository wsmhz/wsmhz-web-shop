package com.wsmhz.web.shop.front.web.controller;
import com.wsmhz.common.business.response.ServerResponse;
import com.wsmhz.web.shop.common.domain.Cart;
import com.wsmhz.web.shop.common.service.CartService;
import com.wsmhz.web.shop.common.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by tangbj on 2018/5/26
 */
@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ServerResponse<CartVo> selectAll(@PathVariable("userId")Long userId){
        return  ServerResponse.createBySuccess(cartService.selectUserCart(userId,null));
    }

    @GetMapping("/{userId}/checked")
    public ServerResponse<CartVo> selectAllChecked(@PathVariable("userId")Long userId){
        return  ServerResponse.createBySuccess(cartService.selectUserCart(userId,true));
    }

    @PostMapping
    public ServerResponse<String> insert(@RequestBody Cart cart){
        Integer result = cartService.insertCart(cart);
        if(result > 0){
            return  ServerResponse.createBySuccess();
        }else{
            return  ServerResponse.createByErrorMessage("加入购物车失败");
        }
    }

    @PutMapping
    public ServerResponse<CartVo> update(@RequestBody Cart cart){
        Integer result = cartService.updateByPrimaryKeySelective(cart);
        if(result > 0){
            return  ServerResponse.createBySuccess();
        }else{
            return  ServerResponse.createByErrorMessage("修改失败");
        }
    }

    @DeleteMapping("/{id}")
    public ServerResponse<CartVo> delete(@PathVariable("id") Long id){
        Integer result = cartService.deleteByPrimaryKey(id);
        if(result > 0){
            return  ServerResponse.createBySuccess();
        }else{
            return  ServerResponse.createByErrorMessage("移出购物车失败");
        }
    }
}
