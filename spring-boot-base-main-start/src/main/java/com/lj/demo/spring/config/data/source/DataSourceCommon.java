package com.lj.demo.spring.config.data.source;

import com.lj.spring.mybatis.common.DynamicMapperPackage;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by lijun on 2019/6/4
 */
@Configuration
@MapperScan(basePackages = {DataSourceCommon.MAPPER_PATH, DynamicMapperPackage.DYNAMIC_PACKAGE_PATH})
public class DataSourceCommon {

    /**
     * 定义Mapper路径
     */
    public static final String MAPPER_PATH = "com.lj.demo.mapper";
}
