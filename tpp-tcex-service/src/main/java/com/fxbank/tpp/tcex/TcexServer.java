package com.fxbank.tpp.tcex;

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
public class TcexServer implements CommandLineRunner {
	
	public static void main(String[] args) {
        SpringApplication.run(TcexServer.class, args);
    }
	
    @Override
    public void run(String... strings) {
    }
    
}
