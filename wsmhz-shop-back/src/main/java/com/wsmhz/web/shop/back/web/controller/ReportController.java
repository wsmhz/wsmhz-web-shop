package com.wsmhz.web.shop.back.web.controller;

import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.web.shop.common.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by tangbj on 2018/7/15
 */
@RestController
@RequestMapping("/manage/report")
public class ReportController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/monthOrders/{month}")
    public ServerResponse monthOrders(@PathVariable("month")int month){
        return ServerResponse.createBySuccess(orderService.selectMonthOrders(month));
    }
}
