package com.lj.spring.redis.serializer;

import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * Created by lijun on 2019/5/28
 */
public class StringRedisKeySerializer extends StringRedisSerializer {

    /**
     * 默认分隔符
     */
    private static final String delimiter = ".";
    private String prefix;
    private int prefixLength;

    public StringRedisKeySerializer() {
        super();
    }

    /**
     * key 默认使用的前缀，编码为 StandardCharsets.UTF_8
     *
     * @param prefix 连接字符
     */
    public StringRedisKeySerializer(String prefix) {
        super();
        Assert.notNull(prefix, "key prefix cant be null!");
        if (prefix.endsWith(delimiter)) {
            this.prefix = prefix;
        } else {
            this.prefix = prefix + delimiter;
        }
        prefixLength = this.prefix.length();
    }


    @Override
    public String deserialize(byte[] bytes) {
        final String result = super.deserialize(bytes);
        return Objects.nonNull(result) && result.startsWith(prefix) ? result.substring(prefixLength) : result;
    }

    @Override
    public byte[] serialize(String string) {
        return Objects.isNull(string) ? null : super.serialize(prefix + string);
    }

    public String getPrefix() {
        return prefix;
    }
}
