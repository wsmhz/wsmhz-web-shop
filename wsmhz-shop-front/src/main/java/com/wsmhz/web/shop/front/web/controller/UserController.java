package com.wsmhz.web.shop.front.web.controller;

import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.web.shop.common.domain.User;
import com.wsmhz.web.shop.common.service.UserService;
import com.wsmhz.web.shop.front.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by tangbj on 2018/5/21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ServerResponse<UserDto> select(@PathVariable("id")Long id){
        User user = userService.selectByPrimaryKey(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return ServerResponse.createBySuccess(userDto);
    }


}
