package com.wsmhz.web.shop.back.authentication;

import com.google.common.collect.Lists;
import com.wsmhz.authorize.domain.Admin;
import com.wsmhz.authorize.domain.Resource;
import com.wsmhz.authorize.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * create by tangbj on 2018/5/6
 */
@Component
public class RbacUserDetailsService implements UserDetailsService ,SocialUserDetailsService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username){

        logger.info("RBAC登录用户名:" + username);
        Admin admin = adminService.selectByUsername(username);
        if(admin == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        Set<Resource> resources = adminService.selectAllResourceByAdmin(new Admin(admin.getId()), null);
        admin.setResources(Lists.newArrayList(resources));
        return admin;

    }


    @Override
    public SocialUserDetails loadUserByUserId(String s) throws UsernameNotFoundException {
        return null;
    }
}
