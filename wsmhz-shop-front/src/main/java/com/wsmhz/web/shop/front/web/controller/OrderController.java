package com.wsmhz.web.shop.front.web.controller;

import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.web.shop.common.domain.Order;
import com.wsmhz.web.shop.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by tangbj on 2018/5/27
 */
@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

//    @GetMapping()
//    public ServerResponse<CartVo> selectAll(@PathVariable("userId")Long userId){
//        return  ServerResponse.createBySuccess(cartService.selectUserCart(userId,null));
//    }
//
//    @GetMapping("/{userId}/checked")
//    public ServerResponse<CartVo> selectAllChecked(@PathVariable("userId")Long userId){
//        return  ServerResponse.createBySuccess(cartService.selectUserCart(userId,true));
//    }
//
    @PostMapping
    public ServerResponse insert(@RequestBody Order order){
        return orderService.createOrder(order.getUserId(),order.getShippingId());
    }
//
//    @PutMapping
//    public ServerResponse<CartVo> update(@RequestBody Cart cart){
//        Integer result = cartService.updateByPrimaryKeySelective(cart);
//        if(result > 0){
//            return  ServerResponse.createBySuccess();
//        }else{
//            return  ServerResponse.createByErrorMessage("修改失败");
//        }
//    }
}
