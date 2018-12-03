package com.fxbank.cap.esb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/** 
* @ClassName: ESBServer 
* @Description: ESB通讯服务 
* @author ZhouYongwei
* @date 2018年10月2日 下午1:42:59 
*  
*/
@ServletComponentScan
@SpringBootApplication
@EnableScheduling
@ComponentScan("com.fxbank")
public class ESBServer implements CommandLineRunner {
	
	public static void main(String[] args) {
        SpringApplication.run(ESBServer.class, args);
    }
	
    @Override
    public void run(String... strings) {
    }
    
}
