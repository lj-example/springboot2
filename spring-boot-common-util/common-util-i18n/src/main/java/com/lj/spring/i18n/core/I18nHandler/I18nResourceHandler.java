package com.lj.spring.i18n.core.I18nHandler;

import org.springframework.util.Assert;

import java.util.Locale;
import java.util.Objects;

/**
 * 用以存储当前请求的语言信息
 * Created by junli on 2019-06-14
 */
public class I18nResourceHandler {

    /**
     * 存储 多语言信息
     */
    private static ThreadLocal<I18nInfo> I18N_INFO = ThreadLocal.withInitial(I18nInfo::defaultInfo);

    /**
     * 设置所有属性
     *
     * @param folderName 文件夹名称
     * @param locale     语言信息
     */
    public static void setInfo(String folderName, Locale locale) {
        I18nInfo i18nInfo = I18N_INFO.get();
        Assert.isTrue(Objects.nonNull(i18nInfo), "i18n Object is null");
        i18nInfo.setFolderName(folderName);
        i18nInfo.setLocale(locale);
    }

    /**
     * 设置语言信息
     */
    public static void setInfo(Locale locale) {
        I18nInfo i18nInfo = I18N_INFO.get();
        Assert.isTrue(Objects.nonNull(i18nInfo), "i18n Object is null");
        i18nInfo.setLocale(locale);
    }

    /**
     * 设置文件夹名称
     */
    public static void setInfo(String folderName) {
        I18nInfo i18nInfo = I18N_INFO.get();
        Assert.isTrue(Objects.nonNull(i18nInfo), "i18n Object is null");
        i18nInfo.setFolderName(folderName);
    }


    /**
     * 获取文件夹名称
     */
    public static String getFolderName() {
        I18nInfo i18nInfo = I18N_INFO.get();
        Assert.isTrue(Objects.nonNull(i18nInfo), "i18n Object is null");
        return i18nInfo.getFolderName();
    }

    /**
     * 获取语言信息
     */
    public static Locale getLocale() {
        I18nInfo i18nInfo = I18N_INFO.get();
        Assert.isTrue(Objects.nonNull(i18nInfo), "i18n Object is null");
        return i18nInfo.getLocale();
    }

    /**
     * 清空多语言信息
     */
    public static void clean() {
        I18N_INFO.remove();
    }

}

