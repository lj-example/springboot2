package com.lj.spring.common.head;

import lombok.experimental.UtilityClass;

/**
 * 定义的头信息
 * Created by junli on 2019-06-18
 */
@UtilityClass
public class HeadCommon {

    /**
     * 令牌
     */
    public static final String USER_TOKEN = "HEAD-USER-TOKEN";

    /**
     * 来源 - 常用于设备类型 - ios/android
     */
    public static final String PLATFORM = "HEAD-PLATFORM";

    /**
     * 渠道 - 常用于 android 手机不同的渠道号
     */
    public static final String CHANNEL = "HEAD-CHANNEL";

    /**
     * 版本信息
     */
    public static final String VERSION = "HEAD-VERSION";

}
