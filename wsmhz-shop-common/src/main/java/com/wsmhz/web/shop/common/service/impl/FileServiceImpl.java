package com.wsmhz.web.shop.common.service.impl;

import com.google.common.collect.Lists;
import com.wsmhz.web.shop.common.properties.BusinessProperties;
import com.wsmhz.web.shop.common.service.FileService;
import com.wsmhz.web.shop.common.utils.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * create by tangbj on 2018/5/20
 */
@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BusinessProperties businessProperties;

    @Override
    public String upload(MultipartFile file, String path) {
        String filename = file.getOriginalFilename();
        String fileExtensionName = filename.substring(filename.lastIndexOf(".") + 1);

        String fileUploadName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传文件名：{}，上传路径：{}，新文件名：{}",filename,path,fileUploadName);

        File fileDir=new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile=new File(path,fileUploadName);
        try {
            file.transferTo(targetFile);
            //---到这里文件已经上传到项目的fileDir下
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //--targetFile上传到FTP服务器上
            targetFile.delete();
            //--删除项目的fileDir下的文件
        } catch (IllegalStateException | IOException e) {
            logger.error("上传文件失败",e);
            return null;
        }
        return businessProperties.getFtp().getHttpPrefix() + targetFile.getName();
    }

    @Override
    public Boolean delete(String fileName) {
        // 替换掉前缀
        String resultFileName =fileName.replace(businessProperties.getFtp().getHttpPrefix(),"");
        boolean result = false;
        try {
            result = FTPUtil.deleteFile(resultFileName);
        } catch (IOException e) {
            logger.error("删除文件失败",e);
        }
        return result;
    }
}
