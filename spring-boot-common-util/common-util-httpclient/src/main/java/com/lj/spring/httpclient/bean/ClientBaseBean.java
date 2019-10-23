package com.lj.spring.httpclient.bean;

import com.lj.spring.httpclient.enums.ContentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.http.util.EncodingUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by junli on 2019-10-10
 */
@AllArgsConstructor
@Data
public class ClientBaseBean {

    public static final String DEFAULT_ENCODE = "UTF-8";

    public static final Class DEFAULT_RETURN_TYPE = String.class;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求参数 - 拼接在地址后面
     */
    private Map<String, Object> params;

    /**
     * 请求头信息
     */
    private Map<String, Object> head;

    /**
     * 请求类型
     */
    @Builder.Default
    private ContentTypeEnum contentTypeEnum = ContentTypeEnum.APPLICATION_JSON_UTF8_VALUE;

    /**
     * 请求编码
     */
    @Builder.Default
    private String encode = DEFAULT_ENCODE;

    /**
     * 返回结果转换
     */
    @Builder.Default
    private Class resultType = DEFAULT_RETURN_TYPE;

    public ClientBaseBean(String url, Map<String, Object> params, Map<String, Object> head) {
        this.url = url;
        this.params = params;
        this.head = head;
    }

    /**
     * 添加参数信息
     *
     * @param key   key
     * @param value value
     * @return 当前对象
     */
    public ClientBaseBean addParam(String key, String value) {
        if (Objects.isNull(this.params)) {
            this.params = new HashMap<>(8);
        }
        mapAddValue(this.params, key, value);
        return this;
    }

    /**
     * 添加头信息
     *
     * @param key   key
     * @param value value
     * @return 当前对象
     */
    public ClientBaseBean addHead(String key, String value) {
        if (Objects.isNull(this.head)) {
            this.head = new HashMap<>(8);
        }
        mapAddValue(this.head, key, value);
        return this;
    }

    /**
     * 获取请求路径
     *
     * @return
     */
    public String buildRequestUrl() {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        String requestUrl = url;
        if (!CollectionUtils.isEmpty(params)) {
            String sortedParam = params.keySet().stream()
                    .sorted(Comparator.naturalOrder())
                    .map(key -> key + "=" + params.get(key))
                    .collect(Collectors.joining("&"));
            requestUrl += "?" + sortedParam;
        }
        try {
            if (Objects.isNull(encode)) {
                encode = DEFAULT_ENCODE;
            }
            return EncodingUtils.getString(requestUrl.getBytes(encode), encode);
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * map 赋值
     *
     * @param map   原始 map
     * @param key   待添加key
     * @param value 待添加value
     */
    protected static void mapAddValue(Map map, String key, String value) {
        if (!StringUtils.isEmpty(key)) {
            map.put(key, value);
        }
    }

    public ContentTypeEnum getContentTypeEnum() {
        if (Objects.isNull(contentTypeEnum)) {
            return ContentTypeEnum.APPLICATION_JSON_UTF8_VALUE;
        }
        return contentTypeEnum;
    }

    public String getEncode() {
        if (Objects.isNull(encode)) {
            return DEFAULT_ENCODE;
        }
        return encode;
    }

    public Class getResultType() {
        if (Objects.isNull(resultType)) {
            return DEFAULT_RETURN_TYPE;
        }
        return resultType;
    }

}
