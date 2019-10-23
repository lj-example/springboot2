package com.lj.spring.httpclient.core.httpclient;

import com.alibaba.fastjson.JSONObject;
import com.lj.spring.httpclient.bean.*;
import lombok.experimental.UtilityClass;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

/**
 * Created by junli on 2019-10-10
 */
@UtilityClass
public final class HttpClientUtil {

    /**
     * get 请求
     * @param clientBean
     */
    public static <T> T get(@NotNull GetClientBean clientBean) throws Exception {
        CloseableHttpClient client = HttpClientPoolManager.getInstanceHttpClientWithDefaultConfig();
        String requestUrl = clientBean.buildRequestUrl();
        HttpGet httpGet = new HttpGet(requestUrl);
        handleHead(clientBean, httpGet);
        return (T) doRequestAndReturn(clientBean, client, httpGet);
    }

    /**
     * post 请求
     */
    public static <T> T post(@NotNull PostClientBean clientBean) throws Exception {
        CloseableHttpClient client = HttpClientPoolManager.getInstanceHttpClientWithDefaultConfig();
        String requestUrl = clientBean.buildRequestUrl();
        HttpPost httpPost = new HttpPost(requestUrl);
        handleHead(clientBean, httpPost);
        HttpEntity httpEntity = clientBean.getContentTypeEnum()
                .buildRequestEntity(clientBean.getBody(), clientBean.getEncode());
        httpPost.setEntity(httpEntity);
        return (T) doRequestAndReturn(clientBean, client, httpPost);
    }

    /**
     * delete 请求
     */
    public static <T> T delete(@NotNull DeleteClientBean clientBean) throws Exception {
        CloseableHttpClient client = HttpClientPoolManager.getInstanceHttpClientWithDefaultConfig();
        String requestUrl = clientBean.buildRequestUrl();
        HttpDelete httpDelete = new HttpDelete(requestUrl);
        handleHead(clientBean, httpDelete);
        return (T) doRequestAndReturn(clientBean, client, httpDelete);
    }

    /**
     * put 请求
     */
    public static <T> T put(@NotNull PutClientBean clientBean) throws Exception {
        CloseableHttpClient client = HttpClientPoolManager.getInstanceHttpClientWithDefaultConfig();
        String requestUrl = clientBean.buildRequestUrl();
        HttpPut httpPut = new HttpPut(requestUrl);
        handleHead(clientBean, httpPut);
        HttpEntity httpEntity = clientBean.getContentTypeEnum()
                .buildRequestEntity(clientBean.getBody(), clientBean.getEncode());
        httpPut.setEntity(httpEntity);
        return (T) doRequestAndReturn(clientBean, client, httpPut);
    }

    /**
     * 处理头信息
     */
    public static void handleHead(ClientBaseBean clientBaseBean, HttpRequestBase httpRequestBase) {
        if (Objects.nonNull(clientBaseBean.getContentTypeEnum())) {
            httpRequestBase.setHeader("Content-type", clientBaseBean.getContentTypeEnum().getContentType());
        }
        if (!CollectionUtils.isEmpty(clientBaseBean.getHead())) {
            Map<String, Object> headMap = clientBaseBean.getHead();
            headMap.entrySet().stream()
                    .filter(entry -> !StringUtils.isEmpty(entry.getKey()) && Objects.nonNull(entry.getValue()))
                    .forEach(entry -> httpRequestBase.setHeader(entry.getKey(), entry.getValue().toString()));
        }
    }

    /**
     * 校验是否网络请求是否成功
     */
    private static void assetSuccess(CloseableHttpResponse response) throws Exception {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return;
        }
        throw new Exception("error statusCode:" + response.getStatusLine().getStatusCode() + "");
    }

    /**
     * 执行请求并返回结果
     */
    private static Object doRequestAndReturn(ClientBaseBean clientBean, CloseableHttpClient client, HttpRequestBase httpRequestBase) throws Exception {
        try (CloseableHttpResponse response = client.execute(httpRequestBase)) {
            assetSuccess(response);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, clientBean.getEncode());
            EntityUtils.consume(entity);
            if (clientBean.getResultType().isAssignableFrom(ClientBaseBean.DEFAULT_RETURN_TYPE)) {
                return result;
            }
            return JSONObject.parseObject(result, (Type) clientBean.getResultType());
        }
    }
}
