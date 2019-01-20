package com.wsmhz.web.shop.front.authentication;

import com.wsmhz.web.shop.common.domain.User;
import com.wsmhz.web.shop.common.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;


/**
 * create by tangbj on 2018/5/6
 */
@Component
public class ShopUserDetailsService implements UserDetailsService ,SocialUserDetailsService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username){
        logger.info("shop登录用户名:" + username);
        User user = userService.selectByUsername(username);
        return user;

    }

    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        return null;
    }
}
