package com.fxbank.cip.manager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan
@SpringBootApplication
@ComponentScan("com.fxbank")
public class Manager implements CommandLineRunner {

	public static void main(String[] args) {
        SpringApplication.run(Manager.class, args);
    }
	
    @Override
    public void run(String... strings) {
    }

}
