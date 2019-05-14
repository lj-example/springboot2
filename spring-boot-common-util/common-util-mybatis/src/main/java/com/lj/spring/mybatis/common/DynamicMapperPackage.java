package com.lj.spring.mybatis.common;

/**
 * 需要调用方 在配置包扫描时 加上该路径，
 * 此处不放在组件中是为了避免 覆盖调用者 自定义的  sqlSessionFactoryRef 属性
 * Created by lijun on 2019/5/13
 */
public final class DynamicMapperPackage {

    /**
     * 扩展sql mapper 路径，
     * 需要手动配置在 @tk.mybatis.spring.annotation.MapperScan basePackages 路径中
     */
    public static final String DYNAMIC_PACKAGE_PATH = "com.lj.spring.mybatis.mapper";
}
