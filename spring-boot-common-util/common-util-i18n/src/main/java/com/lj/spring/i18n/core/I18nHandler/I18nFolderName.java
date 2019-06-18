package com.lj.spring.i18n.core.I18nHandler;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Created by junli on 2019-06-14
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface I18nFolderName {

    /**
     * 默认文件夹名称
     */
    @AliasFor("value")
    String name() default I18nInfo.DEFAULT_FOLDER_NAME;

    @AliasFor("name")
    String value() default I18nInfo.DEFAULT_FOLDER_NAME;
}
