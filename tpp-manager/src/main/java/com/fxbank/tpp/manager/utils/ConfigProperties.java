package com.fxbank.tpp.manager.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ConfigProperties {

	private  Properties sysConfigProperties;
	
	//同步的静态函数
    private  void loadProperties(String filePath) {
        if (null == sysConfigProperties) {
            try {
                Properties properties = new Properties();
                //加载配置文件
//                InputStream inputStream = ConfigProperties.class.getClassLoader().getResourceAsStream("config.properties");
//                System.out.println("配置文件路径："+ConfigProperties.class.getResourceAsStream("/classes/config.properties"));
//                InputStream inputStream =new FileInputStream(filePath);
                InputStream inputStream = new BufferedInputStream(new FileInputStream(new File(filePath)));
                BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                properties.load(bf);
                sysConfigProperties = properties;
                //System.out.println("配置文件加载完成");
            } catch (Exception e) {
            	System.out.println("config配置文件加载失败");
            	e.printStackTrace();
                //throw new RuntimeException("未找到URL配置文件.");
            }
        }
    }

//    //静态函数，读取参数
//    public static String get(String key) {
//        loadProperties();
//        return sysConfigProperties.getProperty(key);
//    }

    public  Map<String,String> getPropertiesFileMap(String filePath){
        loadProperties(filePath);
        Map<String,String> map=new HashMap<String,String>((Map) sysConfigProperties);
        Set propertySet = map.entrySet();
        for (Object o : propertySet) {
            Map.Entry entry = (Map.Entry) o;
        }
        return map;
    }


    public static void main (String []args) throws IOException {
//        ConfigProperties cp=new ConfigProperties();
//        Map map=cp.getPropertiesFileMap("/Users/LiuS/阜新银行项目/gjj/tpp-root/tpp-manager/etc/test.properties");
//        System.out.println("---------");
        //        File file = new File("/Users/LiuS/阜新银行项目/gjj/tpp-root/tpp-manager/etc/test.properties");
//        FileWriter fw = new FileWriter(file.getAbsoluteFile());
//        BufferedWriter bw = new BufferedWriter(fw);
//        bw.write("222222222");
//        bw.close();
    }
}
