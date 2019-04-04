package com.wsmhz.web.shop.front.mq;
import com.wsmhz.common.business.utils.JsonUtil;
import com.wsmhz.web.shop.front.dto.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by tangbj on 2018/7/28
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void createOrder(OrderMessage orderMessage) {
        String msg = JsonUtil.objToString(orderMessage);
        log.info("发送消息:"+msg);
        amqpTemplate.convertAndSend(RabbitMQConfig.ORDER_QUEUE, msg);
    }
}
