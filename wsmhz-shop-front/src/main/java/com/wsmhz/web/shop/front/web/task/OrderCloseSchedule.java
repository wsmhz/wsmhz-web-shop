package com.wsmhz.web.shop.front.web.task;

import com.wsmhz.web.shop.common.enums.OrderConst;
import com.wsmhz.web.shop.common.properties.BusinessProperties;
import com.wsmhz.web.shop.front.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * create by tangbj on 2018/7/14
 */
@Component
@Slf4j
public class OrderCloseSchedule {

    @Autowired
    private BusinessProperties businessProperties;
    @Autowired
    private PayService payService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     * 定时关单。单台服务器，不支持集群
     */
//    @Scheduled(cron = "0 */1 * * * ?")    //每一分钟
    public void closeOrderTaskV1(){
        log.info("关闭订单定时任务启动");
        int hour = Integer.parseInt(businessProperties.getTask().getOrderCloseTimeHour());
        payService.closeOrder(hour);
        log.info("关闭订单定时任务结束");
    }

    /**
     * 定时关单。使用redis分布式锁，支持集群（强制关闭tomcat可能会导致死锁问题）
     */
//    @Scheduled(cron = "0 */1 * * * ?")    //每一分钟
    public void closeOrderTaskV2(){
        log.info("关闭订单定时任务启动");
        long lockTimeout = Long.parseLong(businessProperties.getTask().getOrderCloseLockTimeout());
        boolean setNxResult = redisTemplate.getConnectionFactory().getConnection().setNX(OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK.getBytes(),String.valueOf(System.currentTimeMillis()+lockTimeout).getBytes());
        if(setNxResult){
            //代表设置成功，获取锁
            closeOrder(OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK,lockTimeout);
        }else{
            log.info("没有获得分布式锁:{}",OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK);
        }
        log.info("关闭订单定时任务结束");
    }

    /**
     * 定时关单。使用redis分布式锁，支持集群(使用时间戳超时时间解决强制关闭服务器而发生的死锁问题)
     */
    @Scheduled(cron = "0 */1 * * * ?")    //每一分钟
    public void closeOrderTaskV3(){
        log.info("==========关闭订单定时任务启动==========");
        long lockTimeout = Long.parseLong(businessProperties.getTask().getOrderCloseLockTimeout());
        boolean setNxResult = redisTemplate.getConnectionFactory().getConnection().setNX(OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK.getBytes(),String.valueOf(System.currentTimeMillis()+lockTimeout).getBytes());
        if(setNxResult){
            closeOrder(OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK,lockTimeout);
        }else{
            //未获取到锁，继续判断，判断时间戳，看是否可以重置并获取到锁
            String lockValueStr = redisTemplate.opsForValue().get(OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK);
            if(lockValueStr != null && System.currentTimeMillis() > Long.parseLong(lockValueStr)){
                String getSetResult = redisTemplate.opsForValue().getAndSet(OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK,String.valueOf(System.currentTimeMillis()+lockTimeout));
                //返回给定的key的旧值，->旧值判断，是否可以获取锁
                //当key没有旧值时，即key不存在时，返回nil ->获取锁
                if(getSetResult == null || StringUtils.equals(lockValueStr,getSetResult)){
                    //真正获取到锁
                    closeOrder(OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK,lockTimeout);
                }else{
                    log.info("没有获取到分布式锁:{}",OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK);
                }
            }else{
                log.info("没有获取到分布式锁:{}",OrderConst.redis_lock.CLOSE_ORDER_TASK_LOCK);
            }
        }
        log.info("==========关闭订单定时任务结束==========");
    }

    private void closeOrder(String lockName,long lockTimeout){
        log.info("=========开始关闭订单============");
        redisTemplate.boundValueOps(lockName).expire(lockTimeout, TimeUnit.MILLISECONDS);//有效期，防止死锁
        log.info("获取锁名{},ThreadName:{}",lockName,Thread.currentThread().getName());
        int hour = Integer.parseInt(businessProperties.getTask().getOrderCloseTimeHour());
        payService.closeOrder(hour);
        redisTemplate.delete(lockName);
        log.info("释放锁名{},ThreadName:{}",lockName,Thread.currentThread().getName());
        log.info("==========结束关闭订单============");
    }
}
