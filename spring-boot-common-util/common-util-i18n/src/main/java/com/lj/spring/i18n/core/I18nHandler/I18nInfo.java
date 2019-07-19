package com.lj.spring.i18n.core.I18nHandler;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * Created by junli on 2019-06-14
 */
@Builder
@Data
public class I18nInfo {

    /**
     * 默认的文件夹名称
     */
    public static final String DEFAULT_FOLDER_NAME = "";

    /**
     * 对应的 folderName
     */
    private String folderName;

    /**
     * 多语言信息
     */
    private Locale locale;

    /**
     * 获取默认属性
     * - 此处最好使用克隆模式
     */
    public static I18nInfo defaultInfo() {
         return new I18nInfo("", Locale.SIMPLIFIED_CHINESE);
    }

}

