package com.lj.spring.web.user.common;

import lombok.AllArgsConstructor;

/**
 * Created by junli on 2019-07-02
 */
public final class Common {

    /**
     * 用以存储用户 token 的key前缀
     */
    public static final String TOKEN_PREFIX = "user:token:";

    /**
     * token 失效时间 90天
     */
    public static final long TOKEN_EXPIRE_SECOND = 60 * 60 * 24 * 90;

    /**
     * 用户 old Token 继续保留时长
     */
    public static final long OLD_TOKEN_EXPIRE_SECOND = 60 * 60 * 24 * 3;

    /**
     * oldToken
     *
     */
    public static final String IS_OLD_TOKEN = "1";

    /**
     * not oldToken
     */
    public static final String NO_OLD_TOKEN = "0";

    /**
     * userToken hash存储的key
     *
     * @see com.lj.spring.web.user.model.UserSessionRedis
     */
    public enum HashKey {
        /**
         * userID
         */
        USER_ID,

        /**
         * mobile
         */
        MOBILE,

        /**
         * deviceSignTime
         */
        DEVICE_SIGN_TIME,

        /**
         * createTime
         */
        CREATE_TIME,

        /**
         * oldToken
         */
        OLD_TOKEN,

        /**
         * 新设备登录时间
         */
        NEW_DEVICE_LOGIN_TIME
        ;
    }
}

