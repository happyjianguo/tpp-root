package com.fxbank.tpp.tcex.TEST;

import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpPut;

public class CallFtpPut {

	public static void main(String[] args) {

		//文件本地路径，例如：String localFilePath = "G:/";
		String localPath = "D:\\esbfile\\";
		
		//文件在ESB文件服务器上的路径，例如：String remoteFilePath = "/ibank/";
		String remotePath = "/cip/";//需要管理员在FTP服务器配置此路径
		
		//带后缀的文件名（格式不限），例如：String fileName = "FS.zip";
		String fileName = "abc.txt";
		
		//本地文件
		String localFile = localPath + fileName;
		
		//ESB文件服务器的文件
		String remoteFile = remotePath + fileName;
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpPut ftpPut = null;
    	try {
    		/**
    		 * localFile	本地文件
    		 * remoteFile    ESB文件服务器文件
    		 * configSet	FTP信息集合
    		 */
    		ftpPut = new FtpPut(localFile, remoteFile, configSet);
			
			//进行文件上传操作，返回值为 remoteFile + @ + 当前文件服务器名称
			String str = ftpPut.doPutFile();//例如：str = "/ibank/FS.zip@server1";
			System.out.println("文件["+str+"]上传成功");
		} catch(Exception e) {
			System.out.println("文件["+fileName+"]上传失败！" + e);
		}finally{
			if (ftpPut != null)
				ftpPut.close();
		}
	}

}
