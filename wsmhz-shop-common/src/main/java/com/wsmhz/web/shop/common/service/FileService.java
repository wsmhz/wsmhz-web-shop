package com.wsmhz.web.shop.common.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * create by tangbj on 2018/5/20
 */
public interface FileService {
    /**
     * 文件上传
     * @param file
     * @param path
     * @return
     */
    String upload(MultipartFile file, String path);

    /**
     * 文件删除
     * @param fileName
     * @return
     */
    Boolean delete(String fileName);
}
