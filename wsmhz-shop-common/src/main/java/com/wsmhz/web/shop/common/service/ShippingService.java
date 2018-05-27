package com.wsmhz.web.shop.common.service;

import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.Shipping;

import java.util.List;

/**
 * create by tangbj on 2018/5/27
 */
public interface ShippingService extends BaseService<Shipping> {

    List<Shipping> selectAllByUserId(Long userId);
}
