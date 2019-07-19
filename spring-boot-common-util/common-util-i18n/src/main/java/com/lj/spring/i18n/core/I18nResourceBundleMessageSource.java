package com.lj.spring.i18n.core;

import com.lj.spring.i18n.config.I18nProperties;
import com.lj.spring.i18n.core.I18nHandler.I18nResourceHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by junli on 2019-06-13
 */
@Slf4j
public class I18nResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

    /**
     * 构建bean
     *
     * @param pathProperties 配置信息
     * @return I18nResourceBundleMessageSource
     */
    public static MessageSource from(I18nProperties pathProperties) {
        Assert.notNull(pathProperties, "i18n pathProperties can not empty");
        Assert.notEmpty(pathProperties.getFilePath(), "i18n filePath can not empty");

        I18nResourceBundleMessageSource i18nResourceBundleMessageSource = new I18nResourceBundleMessageSource();
        i18nResourceBundleMessageSource.setDefaultEncoding(pathProperties.getEncode());
        i18nResourceBundleMessageSource.setCacheSeconds(pathProperties.getCacheSeconds());
        i18nResourceBundleMessageSource.setUseCodeAsDefaultMessage(pathProperties.getUseCodeAsDefaultMessage());

        //设置文件路径
        List<String> collect = pathProperties.getFilePath().stream()
                .filter(path -> !StringUtils.isEmpty(path))
                .distinct()
                .map(path -> {
                    if (!path.startsWith(I18nProperties.DEFAULT_FILE_PATH)) {
                        return buildBaseNameByFolderName(path);
                    }
                    return path;
                })
                .collect(Collectors.toList());
        i18nResourceBundleMessageSource.setBasenames(collect.toArray(new String[]{}));

        ReloadableResourceBundleMessageSource parentSource = new ReloadableResourceBundleMessageSource();
        i18nResourceBundleMessageSource.setParentMessageSource(parentSource);
        return i18nResourceBundleMessageSource;
    }

    /**
     * 重写 key - value 取值
     *
     * @param code   code
     * @param locale 语言
     * @return 对应的 value
     */
    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        final String i18nFolderName = I18nResourceHandler.getFolderName();
        if (StringUtils.isEmpty(i18nFolderName)) {
            return super.resolveCodeWithoutArguments(code, locale);
        }
        final String baseNameByFolderName = buildBaseNameByFolderName(i18nFolderName);
        String trueI18nFolderName = getBasenameSet().stream()
                .filter(baseName -> StringUtils.endsWithIgnoreCase(baseName, baseNameByFolderName))
                .findAny()
                .orElse(null);
        if (Objects.nonNull(trueI18nFolderName)) {
            String result = i18nResolveCodeWithoutArguments(code, locale, baseNameByFolderName);
            if (StringUtils.isEmpty(result)) {
                return super.resolveCodeWithoutArguments(code, locale);
            }
            return result;
        } else {
            log.info("【i18n】fileName {} is not in i18n.properties", i18nFolderName);
            return super.resolveCodeWithoutArguments(code, locale);
        }
    }

    /**
     * 构建baseName
     */
    private static String buildBaseNameByFolderName(String folderName) {
        return I18nProperties.DEFAULT_FILE_PATH + folderName + "/messages";
    }

    /**
     * 根据官方 自定义获取
     */
    private String i18nResolveCodeWithoutArguments(String code, Locale locale, String baseName) {
        if (getCacheMillis() < 0) {
            PropertiesHolder propHolder = getMergedProperties(locale);
            String result = propHolder.getProperty(code);
            if (null != result) {
                return result;
            }
        } else {
            List<String> filenames = calculateAllFilenames(baseName, locale);
            for (String filename : filenames) {
                PropertiesHolder propHolder = getProperties(filename);
                String result = propHolder.getProperty(code);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}