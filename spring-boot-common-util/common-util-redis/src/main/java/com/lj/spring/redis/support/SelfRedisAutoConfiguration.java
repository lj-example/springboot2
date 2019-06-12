package com.lj.spring.redis.support;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lj.spring.redis.common.Common;
import com.lj.spring.redis.serializer.StringRedisKeySerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by lijun on 2019/5/28
 */
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class)
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class SelfRedisAutoConfiguration {

    @Value("${spring.application.name:unknown}")
    private String serverName;

    /**
     * key 序列化方式
     */
    @Bean
    public StringRedisKeySerializer redisKeySerializer() {
        return new StringRedisKeySerializer(serverName);
    }

    /**
     * 不带有 命名空间的 key 序列化
     */
    @Bean(name = "stringRedisKeySerializerWithoutServerName")
    public StringRedisSerializer stringRedisKeySerializerWithoutServerName() {
        return new StringRedisSerializer();
    }

    /**
     * 声明 jackson 序列化
     */
    @Bean
    public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(om);
        return serializer;
    }

    /**
     * StringRedisTemplate
     */
    @Bean(name = Common.STRING_REDIS_TEMPLATE_NAME)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory,
                                                   StringRedisKeySerializer stringRedisKeySerializer,
                                                   Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        stringRedisTemplate.setKeySerializer(stringRedisKeySerializer);
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //使用jackson2Json
        stringRedisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        stringRedisTemplate.afterPropertiesSet();
        return stringRedisTemplate;
    }

    /**
     * 默认 redisTemplate
     */
    @Bean(name = Common.REDIS_TEMPLATE_NAME)
    public RedisTemplate redisTemplate(
            RedisConnectionFactory redisConnectionFactory,
            StringRedisKeySerializer stringRedisKeySerializer, Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        return redisTemplateFrom(redisConnectionFactory, stringRedisKeySerializer, jackson2JsonRedisSerializer);
    }

    @Bean(name = Common.REDIS_TEMPLATE_WITHOUT_PREFIX_NAME)
    public RedisTemplate redisTemplateWithoutPrefix(
            RedisConnectionFactory redisConnectionFactory,
            @Qualifier("stringRedisKeySerializerWithoutServerName") StringRedisSerializer stringRedisSerializer,
            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        return redisTemplateFrom(redisConnectionFactory, stringRedisSerializer, jackson2JsonRedisSerializer);
    }

    /**
     * 构造 RedisTemplate
     *
     * @param redisConnectionFactory      连接信息
     * @param stringRedisKeySerializer    key 序列化方式
     * @param jackson2JsonRedisSerializer value 默认的序列化方式
     * @return redisTemplate
     */
    private static RedisTemplate redisTemplateFrom(RedisConnectionFactory redisConnectionFactory,
                                                   StringRedisSerializer stringRedisKeySerializer,
                                                   Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        final RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(stringRedisKeySerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //使用jackson2Json
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
