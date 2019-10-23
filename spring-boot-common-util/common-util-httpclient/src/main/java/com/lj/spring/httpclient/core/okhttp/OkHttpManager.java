package com.lj.spring.httpclient.core.okhttp;

import com.lj.spring.httpclient.common.Common;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by junli on 2019-10-17
 */
public enum OkHttpManager {
    CLIENT;


    private OkHttpClient okHttpClient;

    OkHttpManager() {
        ConnectionPool connectionPool = new ConnectionPool(Common.DEFAULT_MAX_TOTAL, 5, TimeUnit.MINUTES);
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Common.DEFAULT_SOCKET_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(Common.DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(Common.DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool)
                .retryOnConnectionFailure(true)
                .followRedirects(true)
                .build();
    }

    public OkHttpClient getInstance() {
        return okHttpClient;
    }
}
