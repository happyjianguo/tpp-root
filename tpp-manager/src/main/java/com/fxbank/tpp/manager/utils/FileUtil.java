package com.fxbank.tpp.manager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static String readToString(String fileName, String encoding) {

		FileInputStream in = null;
		String fileBuf = null;
		try {
			File file = new File(fileName);
			Long filelength = file.length();
			byte[] filecontent = new byte[filelength.intValue()];
			in = new FileInputStream(file);
			in.read(filecontent);
			fileBuf = new String(filecontent, encoding);
		} catch (Exception e) {
			logger.error("读取文件异常", e);
			throw new RuntimeException("读取文件异常", e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					logger.error("关闭临时文件异常", e);
				}
		}

		return fileBuf;
	}

}
