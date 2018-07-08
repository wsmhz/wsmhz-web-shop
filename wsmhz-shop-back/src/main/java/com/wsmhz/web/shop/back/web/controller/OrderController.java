package com.wsmhz.web.shop.back.web.controller;

import com.github.pagehelper.PageInfo;
import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.security.core.dto.MybatisPage;
import com.wsmhz.web.shop.common.domain.Order;
import com.wsmhz.web.shop.common.enums.OrderConst;
import com.wsmhz.web.shop.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * create by tangbj on 2018/6/27
 */
@RestController
@RequestMapping("/manage/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/page")
    public ServerResponse<MybatisPage> selectAllOfPage(@RequestParam(value = "pageNum")Integer pageNum,
                                                       @RequestParam(value = "pageSize")Integer pageSize,
                                                       @RequestParam(value = "orderNo",required = false)Long orderNo,
                                                       @RequestParam(value = "status",required = false)OrderConst.OrderStatusEnum status){
        ServerResponse serverResponse = orderService.selectOrderListByUserId(pageNum,pageSize,null,orderNo,status);
        PageInfo pageInfo = (PageInfo) serverResponse.getData();
        return  ServerResponse.createBySuccess(new MybatisPage<>(pageInfo.getTotal(),pageInfo.getList()));
    }

    @GetMapping("/{id}")
    public ServerResponse select(@PathVariable("id")Long id){
        return  orderService.selectOrderDetail(null,id);
    }


    @PutMapping
    public ServerResponse shipment(@RequestBody Order order){
       return orderService.shipment(order.getId());
    }

    @DeleteMapping("/{id}")
    public ServerResponse<String> delete(@PathVariable("id") Long id){
        Integer result = orderService.deleteByPrimaryKey(id);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("删除成功");
        }else{
            return  ServerResponse.createByErrorMessage("删除失败");
        }
    }
}
