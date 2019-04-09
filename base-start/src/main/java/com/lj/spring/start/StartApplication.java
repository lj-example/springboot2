package com.lj.spring.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class StartApplication{

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
