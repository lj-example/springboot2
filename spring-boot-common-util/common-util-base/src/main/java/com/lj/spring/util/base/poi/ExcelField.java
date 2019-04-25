package com.lj.spring.util.base.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lijun on 2019/4/23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     * 导出到Excel中的名字
     */
    String name();

    /**
     * 配置列的名称,对应A,B,C,D....
     */
    String column();

    /**
     * 是否导出数据
     */
    boolean isExport() default true;
}
