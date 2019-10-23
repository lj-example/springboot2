package com.lj.spring.httpclient.common;

import lombok.experimental.UtilityClass;

/**
 * Created by junli on 2019-10-10
 */
@UtilityClass
public class Common {
    
    public final static int DEFAULT_CONNECT_TIMEOUT = 2000;
    public final static int DEFAULT_CONNECT_REQUEST_TIMEOUT = 2000;
    public final static int DEFAULT_READ_TIMEOUT = 5000;
    public final static int DEFAULT_WRITE_TIMEOUT = 2000;
    public final static int DEFAULT_SOCKET_TIMEOUT = DEFAULT_READ_TIMEOUT + DEFAULT_WRITE_TIMEOUT;
    public final static int DEFAULT_KEEPALIVE_TIME = 5 * 60 * 1000; //五分钟

    public final static int DEFAULT_MAX_TOTAL = 400;
    public final static int DEFAULT_MAX_PER_ROUTE = 20;

    /**
     * 请求体没有 参数的时候
     */
    public static final String REQUEST_BODY_NO_KEY = "REQUEST_BODY_NO_KEY";

}
