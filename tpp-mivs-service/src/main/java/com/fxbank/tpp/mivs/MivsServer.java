package com.fxbank.tpp.mivs;

import javax.annotation.Resource;

import com.fxbank.tpp.mivs.mq.MqQaClient;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ServletComponentScan
@SpringBootApplication
@EnableScheduling
@ComponentScan("com.fxbank")
public class MivsServer implements CommandLineRunner {
    
    public static final String REDIS_PREFIX = "mivs.";

    @Resource
    private MqQaClient mq;

	public static void main(String[] args) {
        SpringApplication.run(MivsServer.class, args);
    }
	
    @Override
    public void run(String... strings) {
    }
    
}
