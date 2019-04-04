package com.wsmhz.web.shop.front.web.controller;
import com.wsmhz.common.business.response.ServerResponse;
import com.wsmhz.web.shop.common.domain.Shipping;
import com.wsmhz.web.shop.common.domain.User;
import com.wsmhz.web.shop.common.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * create by tangbj on 2018/5/27
 */
@RestController
@RequestMapping("/api/shipping")
public class ShippingController {
    
    @Autowired
    private ShippingService shippingService;

    @GetMapping()
    public ServerResponse<List<Shipping>> selectAll(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return  ServerResponse.createBySuccess(shippingService.selectAllByUserId(user.getId()));
    }

    @GetMapping("/{id}")
    public ServerResponse<Shipping> select(@PathVariable("id")Long id){
        return  ServerResponse.createBySuccess(shippingService.selectByPrimaryKey(id));
    }

    @PostMapping
    public ServerResponse<String> insert(@RequestBody Shipping shipping){
        Integer result = shippingService.insertSelective(shipping);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("添加新地址成功");
        }else{
            return  ServerResponse.createByErrorMessage("添加失败");
        }
    }

    @PutMapping
    public ServerResponse<String> update(@RequestBody Shipping shipping){
        Integer result = shippingService.updateByPrimaryKeySelective(shipping);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("修改成功");
        }else{
            return  ServerResponse.createByErrorMessage("修改失败");
        }
    }

    @DeleteMapping("/{id}")
    public ServerResponse<String> delete(@PathVariable("id") Long id){
        Integer result = shippingService.deleteByPrimaryKey(id);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("删除成功");
        }else{
            return  ServerResponse.createByErrorMessage("删除失败");
        }
    }
}
