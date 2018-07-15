package com.wsmhz.web.shop.common.properties;

/**
 * 定时任务调度配置项
 * create by tangbj on 2018/7/14
 */
public class TaskProperties {
    /**
     * 定时关单时间,单位小时(例如关闭当前时间2小时前还未支付的订单)
     */
    private String orderCloseTimeHour;
    /**
     * redis关单的分布式锁超时时间,单位毫秒
     */
    private String orderCloseLockTimeout;

    public String getOrderCloseTimeHour() {
        return orderCloseTimeHour;
    }

    public void setOrderCloseTimeHour(String orderCloseTimeHour) {
        this.orderCloseTimeHour = orderCloseTimeHour;
    }

    public String getOrderCloseLockTimeout() {
        return orderCloseLockTimeout;
    }

    public void setOrderCloseLockTimeout(String orderCloseLockTimeout) {
        this.orderCloseLockTimeout = orderCloseLockTimeout;
    }
}
