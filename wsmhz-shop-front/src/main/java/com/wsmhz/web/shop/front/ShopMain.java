package com.wsmhz.web.shop.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by tangbj on 2018/5/6
 */
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.wsmhz.web.shop.common.dao")
@SpringBootApplication
@EnableScheduling
@RestController
@ComponentScan("com.wsmhz")
public class ShopMain {
    public static void main(String[] args) {
        SpringApplication.run(ShopMain.class,args);
    }
}
