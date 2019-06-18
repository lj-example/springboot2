package com.lj.spring.dataSource.core;

import com.alibaba.druid.pool.DruidDataSource;
import com.lj.spring.dataSource.config.DruidDataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by lijun on 2019/4/29
 */
public class DruidDataSourceFactory {

    /**
     * 生成一个 DataSource
     *
     * @param druidProperties 数据库配置信息
     */
    public static DataSource createDataSource(DruidDataSourceProperties druidProperties) throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(druidProperties.getUrl());
        dataSource.setUsername(druidProperties.getUsername());
        dataSource.setPassword(druidProperties.getPassword());
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setDbType("com.alibaba.druid.pool.DruidDataSource");

        if (druidProperties.getInitialSize() != null) {
            dataSource.setInitialSize(druidProperties.getInitialSize());
        }
        if (druidProperties.getDriverClassName() != null) {
            dataSource.setDriverClassName(druidProperties.getDriverClassName());
        }
        if (druidProperties.getMinIdle() != null) {
            dataSource.setMinIdle(druidProperties.getMinIdle());
        }
        if (druidProperties.getMaxActive() != null) {
            dataSource.setMaxActive(druidProperties.getMaxActive());
        }
        if (druidProperties.getMaxWait() != null) {
            dataSource.setMaxWait(druidProperties.getMaxWait());
        }
        if (druidProperties.getTimeBetweenEvictionRunsMillis() != null) {
            dataSource.setTimeBetweenEvictionRunsMillis(druidProperties.getTimeBetweenEvictionRunsMillis());
        }
        if (druidProperties.getMinEvictableIdleTimeMillis() != null) {
            dataSource.setMinEvictableIdleTimeMillis(druidProperties.getMinEvictableIdleTimeMillis());
        }
        if (druidProperties.getValidationQuery() != null) {
            dataSource.setValidationQuery(druidProperties.getValidationQuery());
        }
        dataSource.setPoolPreparedStatements(druidProperties.isPoolPreparedStatements());
        Integer maxPoolSize = druidProperties.getMaxPoolPreparedStatementPerConnectionSize();
        if (druidProperties.isPoolPreparedStatements() && maxPoolSize != null) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolSize);
        }
        dataSource.setTestWhileIdle(druidProperties.isTestWhileIdle());
        dataSource.setTestOnBorrow(druidProperties.isTestOnBorrow());
        dataSource.setTestOnReturn(druidProperties.isTestOnReturn());
        if (maxPoolSize != null) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolSize);
        }
        if (druidProperties.getFilters() != null) {
            dataSource.setFilters(druidProperties.getFilters());
        }
        if (druidProperties.getConnectionProperties() != null) {
            dataSource.setConnectProperties(druidProperties.getConnectionProperties());
        }
        //把所有的监控数据合并在一起
//        dataSource.setUseGlobalDataSourceStat(true);
        return dataSource;
    }
}
