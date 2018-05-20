package com.wsmhz.web.shop.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by tangbj on 2018/5/6
 */
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.wsmhz.authorize.dao","com.wsmhz.web.shop.back.dao"})
@SpringBootApplication
@RestController
@ComponentScan("com.wsmhz")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}
