package com.lj.spring.httpclient.enums;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lj.spring.httpclient.common.Common.REQUEST_BODY_NO_KEY;

/**
 * Created by junli on 2019-10-09
 */
@AllArgsConstructor
@Getter
public enum ContentTypeEnum {
    ALL_VALUE("*/*"),
    APPLICATION_XHTML_XML("application/xhtml+xml"),
    APPLICATION_ATOM_XML("application/atom+xml"),

    APPLICATION_XML_VALUE("application/xml"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    APPLICATION_PDF("application/pdf"),

    APPLICATION_JSON("application/json") {
        @Override
        public HttpEntity buildRequestEntity(Map<String, Object> params, String encoding) {
            if (Objects.nonNull(params) && params.keySet().contains(REQUEST_BODY_NO_KEY)) {
                StringEntity stringEntity = new StringEntity(params.get(REQUEST_BODY_NO_KEY).toString(), encoding);
                stringEntity.setContentType(this.getContentType());
                return stringEntity;
            }
            StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(params), encoding);
            stringEntity.setContentType(this.getContentType());
            return stringEntity;
        }
    },

    APPLICATION_JSON_UTF8_VALUE("application/json;charset=UTF-8") {
        @Override
        public HttpEntity buildRequestEntity(Map<String, Object> params, String encoding) {
            if (Objects.nonNull(params) && params.keySet().contains(REQUEST_BODY_NO_KEY)) {
                return new StringEntity(params.get(REQUEST_BODY_NO_KEY).toString(), encoding);
            }
            StringEntity stringEntity = new StringEntity(JSONObject.toJSONString(params), encoding);
            stringEntity.setContentType(this.getContentType());
            return stringEntity;
        }
    },

    MULTIPART_FORM_DATA_VALUE("multipart/form-data") {
        @Override
        public RequestBody buildOkHttpRequest(Map<String, Object> params) {
            return ContentTypeEnum.buildOkHttpFormRequest(params);
        }
    },

    APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded") {
        @Override
        public RequestBody buildOkHttpRequest(Map<String, Object> params) {
            return ContentTypeEnum.buildOkHttpFormRequest(params);
        }
    },
    ;

    private String contentType;

    /**
     * 构建请求实体
     *
     * @param params   参数
     * @param encoding 编码
     * @return 请求实体
     */
    public HttpEntity buildRequestEntity(Map<String, Object> params, String encoding) throws UnsupportedEncodingException {
        return new UrlEncodedFormEntity(parametersFormMap(params), encoding);
    }

    /**
     * 构建请求的 json 字符
     *
     * @param params 参数
     * @return 请求体
     */
    public RequestBody buildOkHttpRequest(Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            RequestBody.create("", MediaType.parse(contentType));
        }
        String jsonString = JSONObject.toJSONString(params);
        return RequestBody.create(jsonString, MediaType.parse(contentType));
    }

    /***
     * 构建请求参数
     */
    private static List<? extends NameValuePair> parametersFormMap(Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            return new ArrayList<>();
        }
        return params.entrySet().stream()
                .filter(entry -> !StringUtils.isEmpty(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .map(entry -> new BasicNameValuePair(entry.getKey(), entry.getValue().toString()))
                .collect(Collectors.toList());
    }

    /**
     * 构建 form 类型请求
     *
     * @param params 请求参数
     * @return requestBody
     */
    private static RequestBody buildOkHttpFormRequest(Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            return new FormBody.Builder().build();
        }
        FormBody.Builder builder = new FormBody.Builder();
        params.entrySet().stream()
                .filter(entry -> !StringUtils.isEmpty(entry.getKey()) && Objects.nonNull(entry.getValue()))
                .forEach(entry -> builder.add(entry.getKey(), entry.getValue().toString()));
        return builder.build();

    }
}
