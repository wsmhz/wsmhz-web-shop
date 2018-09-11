package com.wsmhz.web.shop.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * create by tangbj on 2018/5/27
 */
public class OrderConst {

    public enum OrderStatusEnum{

        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭");

        OrderStatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        @JsonCreator
        public static OrderStatusEnum getItem(String value){
            for(OrderStatusEnum item : values()){
                if(item.name().equals(value)){
                    return item;
                }
            }
            return null;
        }

        public static OrderStatusEnum codeOf(int code){
            for(OrderStatusEnum orderStatusEnum : values()){
                if(orderStatusEnum.getCode() == code){
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到订单状态对应的枚举");
        }
    }

    public interface  AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝");

        PayPlatformEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        @JsonCreator
        public static PayPlatformEnum getItem(String value){
            for(PayPlatformEnum item : values()){
                if(item.name().equals(value)){
                    return item;
                }
            }
            return null;
        }

    }

    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"在线支付");

        PaymentTypeEnum(int code,String value){
            this.code = code;
            this.value = value;
        }
        private String value;
        private int code;

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }

        @JsonCreator
        public static PaymentTypeEnum getItem(String value){
            for(PaymentTypeEnum item : values()){
                if(item.name().equals(value)){
                    return item;
                }
            }
            return null;
        }

        public static PaymentTypeEnum codeOf(int code){
            for(PaymentTypeEnum paymentTypeEnum : values()){
                if(paymentTypeEnum.getCode() == code){
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到支付类型对应的枚举");
        }

    }

    public interface  redis_lock{
        String CLOSE_ORDER_TASK_LOCK = "CLOSE_ORDER_TASK_LOCK";
    }

    public interface  redisMessage{
        String CREATE_ORDER_MESSAGE_ = "CREATE_ORDER_MESSAGE_";
        int CREATE_ORDER_MESSAGE_TIMEOUT_HOUR = 2;
    }
}
