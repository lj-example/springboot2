package com.lj.test;

import com.lj.spring.mybatis.common.DynamicMapperPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = {"com.lj.test.dao",DynamicMapperPackage.DYNAMIC_PACKAGE_PATH})
public class StartApplication{

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
