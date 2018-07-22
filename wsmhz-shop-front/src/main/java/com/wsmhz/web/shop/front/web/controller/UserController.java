package com.wsmhz.web.shop.front.web.controller;

import com.wsmhz.security.core.common.ServerResponse;
import com.wsmhz.web.shop.common.domain.User;
import com.wsmhz.web.shop.common.service.UserService;
import com.wsmhz.web.shop.front.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * create by tangbj on 2018/5/21
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    public ServerResponse<UserDto> select(@PathVariable("id")Long id){
        User user = userService.selectByPrimaryKey(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);
        return ServerResponse.createBySuccess(userDto);
    }

    @PostMapping("/register")
    public ServerResponse<String> register(@RequestBody User user){
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        Integer result = userService.insertSelective(user);
        if(result > 0){
            return  ServerResponse.createBySuccessMessage("注册成功");
        }else{
            return  ServerResponse.createByErrorMessage("注冊失败");
        }
    }


}
