package com.lj.spring.httpclient.bean;

import com.lj.spring.httpclient.enums.ContentTypeEnum;
import lombok.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by junli on 2019-10-10
 */
public final class PostClientBean extends ClientBaseBean {

    private Map<String, Object> body;

    @Builder
    public PostClientBean(String url, Map params, Map head, ContentTypeEnum contentTypeEnum, String encode, Class resultType, Map<String, Object> body) {
        super(url, params, head, contentTypeEnum, encode, resultType);
        this.body = body;
    }

    /**
     * 添加请求体信息
     *
     * @param key   key
     * @param value value
     * @return 当前对象
     */
    public PostClientBean addBody(String key, String value) {
        if (Objects.isNull(this.body)){
            this.body = new HashMap<>(8);
        }
        mapAddValue(this.body, key, value);
        return this;
    }

    public Map<String, Object> getBody() {
        return body;
    }
}
