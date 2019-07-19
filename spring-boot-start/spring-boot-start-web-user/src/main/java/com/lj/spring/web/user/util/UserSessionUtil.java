package com.lj.spring.web.user.util;

import com.lj.spring.web.user.common.Common;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

/**
 * Created by junli on 2019-07-02
 */
@UtilityClass
public class UserSessionUtil {

    public static final FastDateFormat DEFAULT_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    /**
     * 通过 user mobile 构建 token key
     *
     * @param mobile 用户手机号
     * @return token
     */
    public static String buildUserTokenKey(String mobile) {
        return Common.TOKEN_PREFIX + mobile;
    }

    /**
     * 获取一个token
     * 采用 uuid 方式生成
     */
    public static String uuIdToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取当前时间
     */
    public static String getNowDateStr() {
        return DEFAULT_FORMAT.format(Date.from(Instant.now()));
    }

    /**
     * 解析时间
     */
    public static Date parseSessionDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return DEFAULT_FORMAT.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}

