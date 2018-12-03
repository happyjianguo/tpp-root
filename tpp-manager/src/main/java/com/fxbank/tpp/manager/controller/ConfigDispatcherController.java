package com.fxbank.tpp.manager.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.mybatis.generator.api.IntrospectedTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fxbank.cip.base.common.MyJedis;
import com.fxbank.tpp.manager.utils.ConfigProperties;

import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisSentinelPool;

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


	@RequestMapping("/config/redisFileList")
	public String redisFileList(@ModelAttribute(value="path") String path){

		return "config/redis_file_list";
	}

	@ResponseBody
	@RequestMapping("/config/redisFileListData")
	public String redisFileListData(String path, Model model){
		JSONObject json=new JSONObject();
		json.put("msg","");
		json.put("code",0);
		try {
			File or = new File(path);
			File[] files = or.listFiles();
//		json.put("count",files.length);
			JSONArray jsonArray=new JSONArray();
			for (File file : files) {
				if (file.isFile()) {
					JSONObject j=new JSONObject();
					j.put("fileName",file.getName());
					jsonArray.add(j);
				}
			}
			json.put("data",jsonArray.toArray());
		}catch (Exception e){
			System.out.println("查询文件失败");
		}
		return json.toString();
	}

	@RequestMapping("/config/redisEdit")
	public String edit(String filePath,Model model){
		ConfigProperties cp=new ConfigProperties();
		Map<String,String> map= cp.getPropertiesFileMap(filePath);
		model.addAttribute("map",map);
		model.addAttribute("filePath",filePath);
		return "config/redis_file_edit";
	}

	@ResponseBody
	@RequestMapping("/config/saveRedis")
	public String saveRedis(String map,String filePath){
		JSONObject json=new JSONObject();
		try {
			File file = new File(filePath);
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			JSONObject mapJson=JSONObject.parseObject(map);
			StringBuffer sb=new StringBuffer();
			for (Map.Entry<String, Object> entry : mapJson.entrySet()) {
//				System.out.println(entry.getKey() + ":" + entry.getValue());
				sb.append(entry.getKey()+"="+entry.getValue());
				sb.append("\n");
			}
			bw.write(sb.toString());
			bw.close();
			json.put("success",true);
			json.put("message","保存成功");
		}catch (Exception e){
			json.put("success",false);
			json.put("message","保存失败"+e);
		}

		return json.toString();
	}

	@ResponseBody
	@RequestMapping("/config/delRedisFile")
	public String delRedisFile(String filePath){
		JSONObject json=new JSONObject();
		File file=new File(filePath);
		if(file.exists()&&file.isFile())
			file.delete();
		json.put("success",true);
		json.put("message","删除成功");
		return json.toString();
	}


	@RequestMapping("/config/redisFileAdd")
	public String redisFileAdd(@ModelAttribute(value="filePath") String filePath){

		return "config/redis_file_add";
	}

	@ResponseBody
	@RequestMapping("/config/redisFileSave")
	public String redisFileSave(String filePath,String fileName){
		JSONObject json=new JSONObject();
		try {
			File file=new File(filePath+"/"+fileName+".properties");
			if(!file.exists())
				file.createNewFile();
			json.put("success",true);
			json.put("message","新建成功");
		}catch (Exception e){
			json.put("success",false);
			json.put("message","新建失败"+e);
		}
		return json.toString();
	}

	// http://127.0.0.1:5000/tpp/config/refresh.do?path=D://workspaces//TPP//tpp-root//tpp-manager//etc
//	@RequestMapping({ "/config/refresh.do" })
//	public void detectController(String path) throws IOException {
//		if (path == null || path.length() == 0) {
//			throw new RuntimeException("缺少参数path");
//		}
//		refreshDir(path);
//	}


//	private void getPathFiles(String path,List<File> list){
//		File or = new File(path);
//		File[] files = or.listFiles();
//		for (File file : files) {
//			if (file.isFile()) {
//				list.add(file);
//			} else if (file.isDirectory()) {
//				refreshDir(file.getAbsolutePath());
//			}
//		}
//
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
