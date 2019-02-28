package com.fxbank.tpp.manager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fxbank.cip.base.common.MyJedis;

import redis.clients.jedis.Jedis;

/**
 * @ClassName: ConfigDispatcherController
 * @Description: 刷新redis中的配置信息
 * @author ZhouYongwei
 * @date 2018年5月9日 下午3:13:47
 * 
 */
@Controller
@ConditionalOnProperty(name = "spring.redis.enable", havingValue = "true")
public class ConfigDispatcherController {
	private static Logger logger = LoggerFactory.getLogger(ConfigDispatcherController.class);

	@Resource
	private MyJedis myJedis;


	@RequestMapping("/config/redis_refresh")
	public String redisRefresh(){
		return "config/redis_refresh";
	}


	@ResponseBody
	@RequestMapping("/config/refresh")
	public String refresh(String path){
		JSONObject json=new JSONObject();
		try {
			if (path == null || path.length() == 0) {
//				throw new RuntimeException("缺少参数path");
				json.put("success",false);
				json.put("message","缺少参数path");
			}else{
				refreshDir(path);
				json.put("success",true);
				json.put("message","刷新redis成功");
			}

		}catch (Exception e){
			json.put("success",false);
			json.put("message","刷新失败,"+e);
			logger.error("刷新redis信息失败");
		}
		return json.toString();
	}

	// http://127.0.0.1:5000/cap/config/refresh.do?path=D://workspaces//CAP//cap-root//cap-manager//etc
//	@RequestMapping({ "/config/refresh.do" })
//	public void detectController(String path) throws IOException {
//		if (path == null || path.length() == 0) {
//			throw new RuntimeException("缺少参数path");
//		}
//		refreshDir(path);
//	}

	private void refreshDir(String dirName) {
		File or = new File(dirName);
		File[] files = or.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				refreshFile(dirName, file.getName());
			} else if (file.isDirectory()) {
				refreshDir(file.getAbsolutePath());
			}
		}
	}

	public void refreshFile(String dirName, String fileName) {
		Properties ps = new Properties();
		FileInputStream fis = null;
		try {
			String prefix = fileName.substring(0, fileName.lastIndexOf("."));
			String fullName = dirName + File.separator + fileName;
			logger.info("刷新文件[" + fullName + "]");
			fis = new FileInputStream(fullName);
			ps.load(new InputStreamReader(fis, "UTF-8"));
			for (Entry e : ps.entrySet()) {
				String key = prefix + "." + e.getKey();
				String value = (String) e.getValue();
				logger.info("刷新数据[" + key + "][" + value + "]");
				try (Jedis jedis = myJedis.connect()) {
					jedis.set(key, value);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("刷新配置文件错误", e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("关闭文件异常", e);
				}
			}
		}
	}
}
