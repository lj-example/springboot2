package com.lj.spring.redis.core.lock.redisLock;

import java.util.Objects;

/**
 * Created by lijun on 2019/5/29
 */
public class LuaScript {

    /**
     * 释放结果
     */
    private static final Integer UN_LOCK_SUCCESS = 1;
    private static final Integer UN_LOCK_FAIL = 0;

    /**
     * 解锁脚本
     */
    private static String unLockScript;

    static {
        //解锁脚本
        StringBuilder unLockScriptStr = new StringBuilder();
        unLockScriptStr
                .append("if redis.call(\"GET\",KEYS[1]) == ARGV[1] ")
                .append("then ")
                .append("   return redis.call(\"DEL\",KEYS[1]) ")
                .append("else ")
                .append("   return 0 ")
                .append("end ");
        unLockScript = unLockScriptStr.toString();
    }

    /**
     * 获取删除锁的脚本
     */
    public static String getUnLockScript() {
        return unLockScript;
    }

    /**
     * 判断解锁是否成功
     *
     * @param scriptResult 解锁成功
     * @return 解锁成功返回 true
     */
    public static boolean unLockResult(Object scriptResult) {
        return Objects.nonNull(scriptResult)
                && UN_LOCK_SUCCESS.equals((((Number) scriptResult)).intValue());
    }
}
