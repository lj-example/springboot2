package com.lj.demo.controller;

import com.lj.demo.api.RedisTestApi;
import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultSuccess;
import com.lj.spring.redis.common.Common;
import com.lj.spring.redis.core.lock.DistributedLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Created by junli on 2019-07-18
 */
@Slf4j
@RestController
@RequestMapping("redis")
@RequiredArgsConstructor
public class RedisTestController implements RedisTestApi {

    private final RedisTemplate redisTemplate;
    private final DistributedLock distributedLock;

    @Override
    @GetMapping
    public Result redisTemplateTest(@RequestParam String key, @RequestParam String value) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
        return ResultSuccess.of(valueOperations.get(key));
    }

    @Override
    @PutMapping
    public Result redisLockTest() throws InterruptedException {
        final String lockKey = "lockKey";
        distributedLock.lock(lockKey);
        System.out.println(Thread.currentThread().getName() + ".lock");
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + ".will unlock");
        distributedLock.releaseLock(lockKey);
        return ResultSuccess.defaultResultSuccess();
    }
}

