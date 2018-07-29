package com.wsmhz.web.shop.front.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by tangbj on 2018/7/28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMessage {

    private Long userId;

    private Long shippingId;

    private String MessageKey;

}
