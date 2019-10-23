package com.lj.spring.httpclient.core.okhttp;

import com.alibaba.fastjson.JSONObject;
import com.lj.spring.httpclient.bean.*;
import lombok.experimental.UtilityClass;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Created by junli on 2019-10-17
 */
@UtilityClass
public class OkHttpUtil {


    /**
     * get 操作
     */
    public static <T> T get(@NotNull GetClientBean clientBean) throws Exception {
        String requestUrl = clientBean.buildRequestUrl();
        Request getRequest = buildRequestBuilder(clientBean)
                .url(requestUrl)
                .get()
                .build();
        return (T) doRequestAndReturn(clientBean, getRequest);
    }

    /**
     * post 请求
     */
    public static <T> T post(@NotNull PostClientBean postClientBean) throws Exception {
        String requestUrl = postClientBean.buildRequestUrl();
        RequestBody requestBody = postClientBean.getContentTypeEnum().buildOkHttpRequest(postClientBean.getBody());
        Request putRequest = buildRequestBuilder(postClientBean)
                .url(requestUrl)
                .post(requestBody)
                .build();
        return (T) doRequestAndReturn(postClientBean, putRequest);
    }

    /**
     * delete 操作
     */
    public static <T> T delete(@NotNull DeleteClientBean deleteClientBean) throws Exception {
        String requestUrl = deleteClientBean.buildRequestUrl();
        Request deleteRequest = buildRequestBuilder(deleteClientBean)
                .url(requestUrl)
                .delete()
                .build();
        return (T) doRequestAndReturn(deleteClientBean, deleteRequest);
    }

    /**
     * put 请求
     */
    public static <T> T put(@NotNull PutClientBean putClientBean) throws Exception {
        String requestUrl = putClientBean.buildRequestUrl();
        RequestBody requestBody = putClientBean.getContentTypeEnum().buildOkHttpRequest(putClientBean.getBody());
        Request putRequest = buildRequestBuilder(putClientBean)
                .url(requestUrl)
                .put(requestBody)
                .build();
        return (T) doRequestAndReturn(putClientBean, putRequest);
    }

    /**
     * 异步请求
     */
    public static void asyncRequest(Request request,
                                    BiConsumer<Call, IOException> failCallBack,
                                    BiConsumer<Call, Response> successCallBack) {
        OkHttpClient client = OkHttpManager.CLIENT.getInstance();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                failCallBack.accept(call, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                successCallBack.accept(call, response);
            }
        });
    }


    /**
     * 处理头信息
     */
    public static Request.Builder buildRequestBuilder(ClientBaseBean clientBaseBean) {
        Request.Builder requestBuilder = new Request.Builder();

        if (Objects.nonNull(clientBaseBean.getContentTypeEnum())) {
            requestBuilder.addHeader("Content-type", clientBaseBean.getContentTypeEnum().getContentType());
        }
        if (!CollectionUtils.isEmpty(clientBaseBean.getHead())) {
            Map<String, Object> headMap = clientBaseBean.getHead();
            headMap.entrySet().stream()
                    .filter(entry -> !StringUtils.isEmpty(entry.getKey()) && Objects.nonNull(entry.getValue()))
                    .forEach(entry -> requestBuilder.addHeader(entry.getKey(), entry.getValue().toString()));
        }
        return requestBuilder;
    }

    /**
     * 校验是否网络请求是否成功
     */
    private static void assetSuccess(Response response) throws Exception {
        if (response.isSuccessful()) {
            return;
        }
        throw new Exception("error statusCode:" + response.code());
    }


    /**
     * 执行请求并返回结果
     */
    private static Object doRequestAndReturn(ClientBaseBean clientBean, Request request) throws Exception {
        OkHttpClient client = OkHttpManager.CLIENT.getInstance();
        Response response = client.newCall(request).execute();
        assetSuccess(response);
        if (clientBean.getResultType().isAssignableFrom(ClientBaseBean.DEFAULT_RETURN_TYPE)) {
            return response.body().string();
        }
        return JSONObject.parseObject(response.body().string(), (Type) clientBean.getResultType());
    }


}
