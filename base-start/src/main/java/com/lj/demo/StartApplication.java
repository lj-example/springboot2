package com.lj.demo;

import com.lj.demo.spring.config.dataSource.DataSourceCommon;
import com.lj.spring.mybatis.common.DynamicMapperPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@MapperScan(basePackages = {DataSourceCommon.MAPPER_PATH, DynamicMapperPackage.DYNAMIC_PACKAGE_PATH})
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
