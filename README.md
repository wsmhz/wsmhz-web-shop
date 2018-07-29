# wsmhz-web-shop

#### 项目介绍

### shop-java后端项目

项目依赖


```
<dependency>
    <groupId>com.wsmhz</groupId>
    <artifactId>wsmhz-security-browser</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```






1.依赖如上,另一个自己的项目 [wsmhz-security](https://gitee.com/wsmhz/wsmhz-security)

2.项目框架springBoot，springSecurity，Mybatis，Mysql

3.wsmhz-shop-back 模块为管理后台服务端部分，对应的前端项目链接[wsmhz-back-shop](https://gitee.com/wsmhz/wsmhz-back-shop)  网站网址[admin.wsmhz.cn](http://admin.wsmhz.cn)

        3.1.基础服务为完整的RBAC模型支撑

        3.2.产品服务，主要为产品的上下架管理，产品分类管理

        3.3.订单服务，主要为查看各分类订单信息，及产品发货管理

        3.4.报表服务，目前主要为月订单量的各状态的统计情况

4.wsmhz-shop-common 模块为该项目的业务逻辑支撑

5.wsmhz-shop-front 模块为shop前台服务端部分，对应的前端项目链接[wsmhz-front-shop](https://gitee.com/wsmhz/wsmhz-front-shop)  网站网址[www.wsmhz.cn](http://www.wsmhz.cn)

       5.1.完整的电商购物流程

       5.2.支持沙箱版支付宝扫码支付
![输入图片说明](https://images.gitee.com/uploads/images/2018/0723/230239_66379f8b_1294661.png "支付.png")


6.关键业务点

        6.1.使用Redis分布式锁解决在集群下的订单定时关单任务

        6.2.使用RabbitMQ,解决在高并发下的订单下单请求

        6.3.使用线上Nginx和FTP实现图片等资源的请求代理服务
