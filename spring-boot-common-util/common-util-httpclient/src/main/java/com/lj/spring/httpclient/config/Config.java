package com.lj.spring.httpclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.lj.spring.httpclient.common.Common.*;

/**
 * Created by junli on 2019-10-18
 */
@Data
@ConfigurationProperties(prefix = "spring.http.rest-client")
public class Config {

    /**
     * 连接超时
     */
    public Integer connectTimeout = DEFAULT_CONNECT_TIMEOUT;

    /**
     * 请求超时
     */
    public Integer requestTimeout = DEFAULT_CONNECT_REQUEST_TIMEOUT;

    /**
     * 读取超时
     */
    public Integer readTimeout = DEFAULT_READ_TIMEOUT;

    /**
     * 写入超时
     */
    public Integer writeTimeout = DEFAULT_WRITE_TIMEOUT;

    /**
     * socketTimeout
     */
    public Integer socketTimeout = DEFAULT_READ_TIMEOUT + DEFAULT_WRITE_TIMEOUT;

    /**
     * 连接保持时间
     */
    public Integer keepAliveTime = DEFAULT_KEEPALIVE_TIME; //五分钟

    /**
     * 最大连接数
     */
    public Integer maxTotal = 400;

    /**
     * 路由次数
     */
    public Integer maxPreRoute = 20;
}
