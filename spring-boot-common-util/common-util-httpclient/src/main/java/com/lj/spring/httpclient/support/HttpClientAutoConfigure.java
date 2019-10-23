package com.lj.spring.httpclient.support;

import com.lj.spring.httpclient.config.Config;
import com.lj.spring.httpclient.core.httpclient.HttpClientPoolManager;
import lombok.RequiredArgsConstructor;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.lj.spring.httpclient.core.httpclient.HttpClientPoolManager.sslContext;

/**
 * Created by junli on 2019-10-18
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(Config.class)
public class HttpClientAutoConfigure {

    private final Config config;

    @Bean
    @ConditionalOnClass(HttpClient.class)
    @ConditionalOnMissingBean(ClientHttpRequestFactory.class)
    @ConditionalOnProperty(name = "spring.http.rest-client.httpClient", havingValue = "enable")
    public ClientHttpRequestFactory httpClientHttpRequestFactory() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(config.getConnectTimeout())
                .setConnectionRequestTimeout(config.getRequestTimeout())
                .setSocketTimeout(config.getConnectTimeout())
                .setRedirectsEnabled(true)
                .build();

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslContext()))
                .build();
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
        manager.setMaxTotal(config.getMaxTotal());
        manager.setDefaultMaxPerRoute(config.getMaxPreRoute());
        manager.setValidateAfterInactivity(4000);

        CloseableHttpClient client = HttpClientPoolManager.buildHttpClient(requestConfig, manager);
        return new HttpComponentsClientHttpRequestFactory(client);
    }

    @Bean
    @ConditionalOnClass(OkHttpClient.class)
    @ConditionalOnMissingBean(ClientHttpRequestFactory.class)
    @ConditionalOnProperty(name = "spring.http.rest-client.okHttp3", havingValue = "enable", matchIfMissing = true)
    public ClientHttpRequestFactory okHttpClientHttpRequestFactory() {
        ConnectionPool connectionPool = new ConnectionPool(config.getMaxPreRoute(), 5, TimeUnit.MINUTES);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(config.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(config.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(config.getReadTimeout(), TimeUnit.MILLISECONDS)
                .connectionPool(connectionPool)
                .retryOnConnectionFailure(true)
                .followRedirects(true)
                .build();
        return new OkHttp3ClientHttpRequestFactory(okHttpClient);
    }

    @Bean
    @Primary
    public RestTemplate restTemplate(@Autowired(required = false) ClientHttpRequestFactory clientHttpRequestFactory) {
        return Objects.isNull(clientHttpRequestFactory) ? new RestTemplate() : new RestTemplate(clientHttpRequestFactory);
    }

}
