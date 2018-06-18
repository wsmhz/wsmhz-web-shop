package com.wsmhz.web.shop.front.web.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.web.shop.common.domain.Order;
import com.wsmhz.web.shop.common.domain.User;
import com.wsmhz.web.shop.common.enums.OrderConst;
import com.wsmhz.web.shop.common.service.OrderService;
import com.wsmhz.web.shop.front.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * create by tangbj on 2018/5/27
 */
@RestController
@RequestMapping("order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    @GetMapping("/page")
    public ServerResponse selectAll(@RequestParam(value = "pageNum")Integer pageNum,
                                    @RequestParam(value = "pageSize")Integer pageSize,
                                    @RequestParam(value = "orderNo",required = false)Long orderNo,
                                    @RequestParam(value = "status",required = false)OrderConst.OrderStatusEnum status,
                                    Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return  orderService.selectOrderListByUserId(pageNum,pageSize,user.getId(),orderNo,status);
    }

    @PostMapping
    public ServerResponse insert(@RequestBody Order order){
        return orderService.createOrder(order.getUserId(),order.getShippingId());
    }

    @PostMapping("/pay")
    public ServerResponse pay(@RequestBody Order order, HttpServletRequest request){
        String path = request.getSession().getServletContext().getRealPath("upload");
        return payService.pay(order.getOrderNo(),order.getUserId(),path);
    }

    @RequestMapping("/aliPayCallback")
    @ResponseBody
    public Object aliPayCallback(HttpServletRequest request){
        Map<String,String> params = Maps.newHashMap();

        Map requestParams = request.getParameterMap();
        for(Iterator iter = requestParams.keySet().iterator(); iter.hasNext();){
            String name = (String)iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for(int i = 0 ; i <values.length;i++){
                valueStr = (i == values.length -1)?valueStr + values[i]:valueStr + values[i]+",";
            }
            params.put(name,valueStr);
        }
        logger.info("支付宝回调,sign:{},trade_status:{},参数:{}",params.get("sign"),params.get("trade_status"),params.toString());

        //验证回调的正确性,是不是支付宝发的.还要避免重复通知.
        params.remove("sign_type");
        try {
            boolean aliPayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());

            if(!aliPayRSACheckedV2){
                return ServerResponse.createByErrorMessage("非法请求,验证不通过");
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝验证回调异常",e);
        }
        //todo 验证各种数据
        ServerResponse serverResponse = payService.aliPayCallback(params);
        if(serverResponse.isSuccess()){
            return OrderConst.AlipayCallback.RESPONSE_SUCCESS;
        }
        return OrderConst.AlipayCallback.RESPONSE_FAILED;
    }


    @GetMapping("/status/{orderNo}")
    public ServerResponse<Boolean> queryOrderPayStatus(@PathVariable("orderNo") Long orderNo,Authentication authentication){
        User user = (User) authentication.getPrincipal();
        ServerResponse serverResponse = orderService.queryOrderPayStatus(user.getId(),orderNo);
        if(serverResponse.isSuccess()){
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createBySuccess(false);
    }

    @PutMapping
    public ServerResponse cancel(@RequestBody Order order){
        return orderService.cancel(order.getUserId(),order.getOrderNo());

    }
}
