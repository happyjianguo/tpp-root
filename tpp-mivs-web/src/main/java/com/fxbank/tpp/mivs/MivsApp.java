package com.fxbank.tpp.mivs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ServletComponentScan
@SpringBootApplication
@ComponentScan("com.fxbank")
public class MivsApp implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MivsApp.class, args);
    }

    @Override
    public void run(String... strings) {
    }

}
