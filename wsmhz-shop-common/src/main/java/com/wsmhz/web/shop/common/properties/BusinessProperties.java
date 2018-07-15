package com.wsmhz.web.shop.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * create by tangbj on 2018/5/20
 */
@PropertySource(value = "classpath:config/business.properties")
@ConfigurationProperties(prefix = "wsmhz")
@Component
public class BusinessProperties {

    private FtpProperties ftp;

    private TaskProperties task;

    public FtpProperties getFtp() {
        return ftp;
    }

    public void setFtp(FtpProperties ftp) {
        this.ftp = ftp;
    }

    public TaskProperties getTask() {
        return task;
    }

    public void setTask(TaskProperties task) {
        this.task = task;
    }
}
