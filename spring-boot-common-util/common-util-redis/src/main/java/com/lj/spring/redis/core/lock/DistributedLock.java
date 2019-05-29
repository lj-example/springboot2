package com.lj.spring.redis.core.lock;

/**
 * Created by lijun on 2019/5/29
 */
public interface DistributedLock {

    /**
     * 默认超时时间
     */
    long TIMEOUT_MILLIS = 30000;

    /**
     * 重试次数
     */
    public int RETRY_TIMES = Integer.MAX_VALUE;

    /**
     * 重试间隔(毫秒) 1s = 1000 Mills
     */
    long SLEEP_MILLIS = 500;

    /**
     * 加锁
     *
     * @param key key
     * @return 成功返回true
     */
    boolean lock(String key);

    /**
     * 加锁
     *
     * @param key           key
     * @param timeoutMillis 锁时长
     * @return 成功返回true
     */
    boolean lock(String key, long timeoutMillis);

    /**
     * 加锁
     *
     * @param key         key
     * @param retryTimes  重试次数
     * @param sleepMillis 重试间隔
     * @return 成功返回true
     */
    boolean lock(String key, int retryTimes, long sleepMillis);

    /**
     * 加锁
     *
     * @param key           key
     * @param timeoutMillis 锁时长
     * @param retryTimes    重试次数
     * @param sleepMillis   重试间隔
     * @return 成功返回true
     */
    boolean lock(String key, long timeoutMillis, int retryTimes, long sleepMillis);

    /**
     * 解锁
     *
     * @param key key
     * @return 成功返回true
     */
    boolean releaseLock(String key);
}
