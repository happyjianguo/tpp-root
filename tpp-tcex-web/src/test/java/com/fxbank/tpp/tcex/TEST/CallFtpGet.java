package com.fxbank.tpp.tcex.TEST;

import com.dcfs.esb.ftp.client.FtpClientConfigSet;
import com.dcfs.esb.ftp.client.FtpGet;
import com.dcfs.esb.ftp.server.error.FtpException;

public class CallFtpGet {
	public static void main(String[] args) {

		// 文件本地路径，例如：String localFilePath = "G:/";
		String localPath = "D:\\esbfile\\";

		// 文件在ESB文件服务器上的路径，例如：String remoteFilePath = "/ibank/";
		String remotePath = "/cip/";// 需要管理员在FTP服务器配置此路径

		// 带后缀的文件名（格式不限），例如：String fileName = "FS.zip";
		String fileName = "abc.txt";

		// 本地文件
		String localFile = localPath + fileName;

		// ESB文件服务器的文件
		String remoteFile = remotePath + fileName;
		FtpClientConfigSet configSet = new FtpClientConfigSet();
		FtpGet ftpGet = null;
		try {
			/**
			 * localFile 本地文件 remoteFile ESB文件服务器文件 configSet FTP信息集合
			 */
			ftpGet = new FtpGet(remoteFile, localFile, configSet);

			// 进行文件上传操作，返回值为 true/false,true代表文件下载成功，false代表文件下载失败
			boolean flag = ftpGet.doGetFile();// 例如：str =
												// "/ibank/FS.zip@server1";
			System.out.println("文件[" + fileName + "]下载成功");
		} catch (Exception e) {
			System.out.println("文件[" + fileName + "]下载失败！" + e);
		} finally {
			if (ftpGet != null) {
				try {
					ftpGet.close();
				} catch (FtpException e) {
					System.out.println("关闭连接失败！" + e);
				}
			}
		}
	}

}
