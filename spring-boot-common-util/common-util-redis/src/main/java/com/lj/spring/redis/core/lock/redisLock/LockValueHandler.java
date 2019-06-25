package com.lj.spring.redis.core.lock.redisLock;

/**
 * Created by lijun on 2019/5/29
 */
public class LockValueHandler {

    /**
     * 用以存储当前线程使用加锁的value值
     */
    private static ThreadLocal<String> LOCAL_VALUE = ThreadLocal.withInitial(String::new);

    /**
     * 设置本地数据
     *
     * @param value 待设置数据
     */
    static void set(String value) {
        LOCAL_VALUE.set(value);
    }

    /**
     * 获取数据
     *
     * @return 数据
     */
    public static String get() {
        return LOCAL_VALUE.get();
    }

    /**
     * 清除本地线程数据
     */
    static void clean() {
        LOCAL_VALUE.remove();
    }
}
