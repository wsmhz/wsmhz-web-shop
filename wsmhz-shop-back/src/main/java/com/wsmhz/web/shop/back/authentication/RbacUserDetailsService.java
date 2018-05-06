package com.wsmhz.web.shop.back.authentication;

import com.wsmhz.authorize.domain.Admin;
import com.wsmhz.authorize.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * create by tangbj on 2018/5/6
 */
@Component
public class RbacUserDetailsService implements UserDetailsService ,SocialUserDetailsService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private AdminService adminService;
    @Autowired
    private PasswordEncoder passwordEncoder; //可经过配置自定义的密码加密

    @Override
    public UserDetails loadUserByUsername(String username){

        logger.info("RBAC登录用户名:" + username);
        Admin admin = adminService.selectByUsername(username);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return admin;

    }


    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        return null;
    }
}
