package com.wsmhz.web.shop.front.mq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create by tangbj on 2018/7/28
 */
@Configuration
public class RabbitMQConfig {

    public static final String ORDER_QUEUE = "order_queue";

    /**
     * 订单消息队列
     * @return
     */
    @Bean
    public Queue queue(){
        return new Queue(ORDER_QUEUE);
    }
}
