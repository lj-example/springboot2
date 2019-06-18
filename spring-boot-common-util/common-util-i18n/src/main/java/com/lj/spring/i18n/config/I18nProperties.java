package com.lj.spring.i18n.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.lj.spring.i18n.config.I18nProperties.*;

/**
 * Created by junli on 2019-06-13
 */
@Data
@ConfigurationProperties(prefix = "i18n")
@PropertySource(DEFAULT_FILE_PROPERTY_SOURCE)
@Configuration
public class I18nProperties {

    public static final String DEFAULT_FILE_PATH = "classpath:i18n/";

    /**
     * 默认配置文件路径
     */
    public static final String DEFAULT_FILE_PROPERTY_SOURCE = DEFAULT_FILE_PATH + "i18n.properties";


    /**
     * 文件路径集合
     */
    private List<String> filePath;

    /**
     * 缓存时间
     */
    private Integer cacheSeconds = 5 * 60;

    /**
     * 默认编码
     */
    private String encode = StandardCharsets.UTF_8.name();

    /**
     * 在没找到对应的文本时是否返回key 而不是抛出错误信息 默认抛出错误信息
     */
    private Boolean useCodeAsDefaultMessage = false;

}

