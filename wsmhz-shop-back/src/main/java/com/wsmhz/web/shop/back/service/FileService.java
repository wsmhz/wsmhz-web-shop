package com.wsmhz.web.shop.back.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * create by tangbj on 2018/5/20
 */
public interface FileService {

    String upload(MultipartFile file, String path);

    Boolean delete(String fileName);
}
