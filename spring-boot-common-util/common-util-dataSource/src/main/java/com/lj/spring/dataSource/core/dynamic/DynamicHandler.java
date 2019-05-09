package com.lj.spring.dataSource.core.dynamic;

/**
 * 多数据源控制
 * Created by lijun on 2019/5/8
 */
public final class DynamicHandler {

    /**
     * 存储当前数据库 对应的 key
     */
    private static ThreadLocal<String> DATA_SOURCE_KEY = ThreadLocal.withInitial(() -> DynamicCommon.MASTER_NAME);

    /**
     * 设置数据源
     */
    public static void set(String dataSourceKey) {
        DATA_SOURCE_KEY.set(dataSourceKey);
    }

    /**
     * 获取数据源
     */
    public static String get() {
        return DATA_SOURCE_KEY.get();
    }

    /**
     * 清空本地线程
     */
    public static void clean() {
        DATA_SOURCE_KEY.remove();
    }
}
