package com.lj.spring.web.version.common;

import com.sun.org.apache.regexp.internal.RE;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by junli on 2019-09-05
 */
@UtilityClass
public class VersionUtil {

    /**
     * 版本号匹配正则
     */
    private final Pattern COMPILE_NUMBER = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");

    /**
     * 版本信息默认前缀
     */
    private static final String VERSION_PREFIX = "v";

    /**
     * 统一处理 version 版本配置
     * 2 -> v2;
     *
     * @param version 原始版本信息
     * @return 格式化之后的版本信息
     */
    public static String formatVersionStr(String version) {
        if (StringUtils.isBlank(version)) {
            return version;
        }
        if (version.startsWith(VERSION_PREFIX)) {
            return version;
        }
        return VERSION_PREFIX + version;
    }

    /**
     * 从字符串中提取版本号
     *
     * @param version 版本号字符串
     * @return 数字版本信息
     */
    public static String getVersionNumber(String version) {
        if (StringUtils.isBlank(version)) {
            return version;
        }
        Matcher matcher = COMPILE_NUMBER.matcher(version);
        return matcher.find() ? matcher.group() : version;
    }
}
