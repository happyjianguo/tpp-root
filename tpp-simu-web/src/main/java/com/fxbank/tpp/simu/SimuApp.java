/**   
* @Title: SimuApp.java 
* @Package com.fxbank.tpp.simu 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2020年1月13日 上午9:53:15 
* @version V1.0   
*/
package com.fxbank.tpp.simu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/** 
* @ClassName: SimuApp 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author YePuLiang
* @date 2020年1月13日 上午9:53:15 
*  
*/
@ServletComponentScan
@SpringBootApplication
@ComponentScan("com.fxbank")
public class SimuApp implements CommandLineRunner {
	
	public static final String[] systemIds = {"cnaps2","ecif","edp","fcs","ncpp","ses","smb","sms","upps"};


    public static void main(String[] args) {
        SpringApplication.run(SimuApp.class, args);
    }


    @Override
    public void run(String... strings) {
    }
}
