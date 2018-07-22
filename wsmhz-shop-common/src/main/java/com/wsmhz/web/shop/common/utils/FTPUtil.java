package com.wsmhz.web.shop.common.utils;

import com.wsmhz.web.shop.common.properties.BusinessProperties;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * create by tangbj on 2018/5/20
 */
@Component
public class FTPUtil {
	private static final Logger log=LoggerFactory.getLogger(FTPUtil.class);
	
	private static String ftpIp;
	private static String ftpUser;
	private static String ftpPwd;
	
	private String ip;
	private int port;
	private String user;
	private String pwd;
	private FTPClient ftpClient;

	public FTPUtil() {
	}

	@Autowired
	private BusinessProperties businessProperties;

	@PostConstruct
	public void init() {
	    FTPUtil.ftpIp = businessProperties.getFtp().getIp();
		FTPUtil.ftpUser = businessProperties.getFtp().getUser();
		FTPUtil.ftpPwd = businessProperties.getFtp().getPwd();
	}

	public  static boolean uploadFile(List<File> fileList) throws IOException{
		FTPUtil ftPutil=new FTPUtil(ftpIp, 21, ftpUser, ftpPwd);
		log.info("开始连接FTP服务器");
		boolean result=ftPutil.uploadFile("img", fileList);
		log.info("结束上传，上传结果:{}",result);
		return result;
	}
	public  static boolean deleteFile(String fileName) throws IOException{
		FTPUtil ftPutil=new FTPUtil(ftpIp, 21, ftpUser, ftpPwd);
		log.info("开始连接FTP服务器");
		boolean result=ftPutil.deleteFile("img", fileName);
		log.info("结束删除，删除结果:{}",result);
		return result;
	}
	
	
	private  boolean deleteFile(String remotePath,String fileName) throws IOException{
		boolean deleted=true;
		if(connectServer(this.ip,this.port,this.user,this.pwd)){
			try {
				ftpClient.changeWorkingDirectory(remotePath);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				deleted=ftpClient.deleteFile(fileName);
			} catch (IOException e) {
				log.error("删除文件异常",e);
			}finally{
				ftpClient.disconnect();
			}
		}
		return deleted;
	}
	
	
	private boolean uploadFile(String remotePath,List<File> fileList) throws IOException{
		boolean uploaded=false;
		FileInputStream fis=null;
		if(connectServer(this.ip,this.port,this.user,this.pwd)){
			try {
				ftpClient.changeWorkingDirectory(remotePath);
				ftpClient.setBufferSize(1024);
				ftpClient.setControlEncoding("UTF-8");
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				ftpClient.enterLocalPassiveMode();
				for (File fileItem : fileList) {
					fis=new FileInputStream(fileItem);
					uploaded=ftpClient.storeFile(fileItem.getName(), fis);
				}
			} catch (IOException e) {
				log.error("上传文件异常",e);
			}finally{
				fis.close();
				ftpClient.disconnect();
			}
		}
		return uploaded;		
	}
	private boolean connectServer(String ip,int port,String user,String pwd){
		boolean isSuccess=false;
		ftpClient=new FTPClient();
		try {
			ftpClient.connect(ip);
			isSuccess=ftpClient.login(user, pwd);
		} catch (IOException e) {
			log.error("连接FTP服务器异常",e);
		}
		return isSuccess;
	}
	
	public FTPUtil(String ip, int port, String user, String pwd) {
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	


}
