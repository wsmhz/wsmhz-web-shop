package com.wsmhz.web.shop.front.mq;
import com.wsmhz.common.business.utils.JsonUtil;
import com.wsmhz.web.shop.common.service.OrderService;
import com.wsmhz.web.shop.front.dto.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by tangbj on 2018/7/28
 */
@Service
@Slf4j
public class MQReceiver {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void receiveCreateOrder(String message) {
        log.info("接收到消息:"+message);
        OrderMessage orderMessage = JsonUtil.stringToObj(message, OrderMessage.class);
        Long userId = orderMessage.getUserId();
        Long shippingId = orderMessage.getShippingId();
        String messageKey = orderMessage.getMessageKey();
        if(userId != null && shippingId != null){
            log.info("开始创建订单");
            orderService.createOrder(userId,shippingId,messageKey);
        }else {
            log.info("参数异常，创建订单失败");
        }
    }
}
