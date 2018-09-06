package com.wsmhz.web.shop.common.service.impl;
import com.wsmhz.common.business.service.BaseServiceImpl;
import com.wsmhz.web.shop.common.dao.UserMapper;
import com.wsmhz.web.shop.common.domain.User;
import com.wsmhz.web.shop.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * create by tangbj on 2018/5/21
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectByUsername(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        return (User) this.userMapper.selectOneByExample(example);
    }
}
