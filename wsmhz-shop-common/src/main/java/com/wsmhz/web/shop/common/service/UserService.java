package com.wsmhz.web.shop.common.service;

import com.wsmhz.security.core.service.BaseService;
import com.wsmhz.web.shop.common.domain.User;

/**
 * create by tangbj on 2018/5/21
 */
public interface UserService extends BaseService<User> {

    User selectByUsername(String username);
}
