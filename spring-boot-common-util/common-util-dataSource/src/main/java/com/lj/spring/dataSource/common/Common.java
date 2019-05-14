package com.lj.spring.dataSource.common;

/**
 * 常量
 * Created by lijun on 2019/5/14
 */
public final class Common {

    /**
     * 主数据 bean 名称
     */
    public static final String MASTER_DATA_SOURCE_NAME = "masterDataSource";

    /**
     * 多数据源 bean 名称
     */
    public static final String MULTI_DATA_SOURCE_NAME = "multiDataSources";

    /**
     * mybatis sqlSessionFactory 名称
     */
    public static final String MYBATIS_SQL_SESSION_FACTORY_NAME = "primarySqlSessionFactory";

    /**
     * mybatis sqlSessionTemplate 名称
     */
    public static final String MYBATIS_SQL_SESSION_TEMPLATE_NAME = "primarySqlSessionTemplate";

    /**
     * mybatis transactionManager 名称
     */
    public static final String MYBATIS_TRANSACTION_MANAGER_NAME = "primaryDataSourceTransactionManager";

}
