package com.wsmhz.web.shop.back.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * create by tangbj on 2018/5/18
 */
public class ProductConst {

    public enum StatusEnum {
        ON_SALE(1,"在售"),
        OFF_SALE(-1,"下架"),
        Not_SALE(-10,"删除");

        private String value;
        private int code;

        StatusEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        @JsonCreator
        public static StatusEnum getItem(String value){
            for(StatusEnum item : values()){
                if(item.name().equals(value)){
                    return item;
                }
            }
            return null;
        }

//        @JsonValue
        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum FlagEnum {
        HOT(1,"热销");

        private String value;
        private int code;

        FlagEnum(int code,String value){
            this.code = code;
            this.value = value;
        }

        @JsonCreator
        public static FlagEnum getItem(String value){
            for(FlagEnum item : values()){
                if(item.name().equals(value)){
                    return item;
                }
            }
            return null;
        }

//        @JsonValue
        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }


}
