package com.lj.spring.httpclient.core.httpclient;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import static com.lj.spring.httpclient.common.Common.*;

/**
 * Http 连接池配置
 * Created by junli on 2019-10-10
 */
public class HttpClientPoolManager {

    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;

    private static final RequestConfig DEFAULT_CONFIG = RequestConfig.custom()
            .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
            .setConnectionRequestTimeout(DEFAULT_CONNECT_REQUEST_TIMEOUT)
            .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
            .setRedirectsEnabled(true)
            .build();

    static {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext()))
                .build();
        poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager(registry);
        poolingHttpClientConnectionManager.setMaxTotal(DEFAULT_MAX_TOTAL);
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        poolingHttpClientConnectionManager.setValidateAfterInactivity(4000);
    }

    /**
     * 获取单例默认配置的连接信息
     */
    public static CloseableHttpClient getInstanceHttpClientWithDefaultConfig() {
        return buildHttpClient(DEFAULT_CONFIG);
    }

    /**
     * 建立连接
     *
     * @param requestConfig 请求配置
     * @return 基础连接信息
     */
    public static CloseableHttpClient buildHttpClient(RequestConfig requestConfig) {
        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setRetryHandler(httpRequestRetryHandler())
                .build();
    }

    /**
     * 建立连接
     *
     * @param requestConfig 请求配置
     * @return 基础连接信息
     */
    public static CloseableHttpClient buildHttpClient(RequestConfig requestConfig,PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
        return HttpClients.custom()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .setDefaultRequestConfig(requestConfig)
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setRetryHandler(httpRequestRetryHandler())
                .build();
    }


    /**
     * 获取一个SSLContext
     *
     * @return
     */
    public static SSLContext sslContext() {
        try {
            return SSLContexts.custom()
                    .loadTrustMaterial((X509Certificate[] chain, String authType) -> Boolean.TRUE)
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            return SSLContexts.createDefault();
        }
    }

    /**
     * HttpClient 重试策略
     */
    public static HttpRequestRetryHandler httpRequestRetryHandler() {
        final int maxExecCount = 3;
        return (exception, executionCount, context) -> {
            if (executionCount >= maxExecCount) {
                //如果已经重试了3次，就放弃
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                //如果服务器丢掉了连接，重试
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                //不重试SSL握手异常
                return false;
            }
            if (exception instanceof InterruptedIOException) {
                //超时
                return false;
            }
            if (exception instanceof UnknownHostException) {
                //目标服务器不可达
                return false;
            }
            if (exception instanceof ConnectTimeoutException) {
                //连接被拒绝
                return false;
            }
            if (exception instanceof SSLException) {
                //ssl握手异常
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，重试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        };
    }
}
