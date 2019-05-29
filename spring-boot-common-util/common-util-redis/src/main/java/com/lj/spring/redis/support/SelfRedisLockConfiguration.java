package com.lj.spring.redis.support;

import com.lj.spring.redis.core.lock.DistributedLock;
import com.lj.spring.redis.core.lock.redisLock.RedisDistributedLock;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by lijun on 2019/5/29
 */
@Configuration
@AutoConfigureAfter(RedisTemplate.class)
public class SelfRedisLockConfiguration {

    @Bean
    public DistributedLock distributedLock(RedisTemplate redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }
}
