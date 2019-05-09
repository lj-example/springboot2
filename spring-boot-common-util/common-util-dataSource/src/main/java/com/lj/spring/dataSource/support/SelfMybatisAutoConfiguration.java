package com.lj.spring.dataSource.support;


import com.lj.spring.dataSource.common.Prefix;
import com.lj.spring.dataSource.config.MultiDruidProperties;
import com.lj.spring.dataSource.core.dynamic.DynamicDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import static com.lj.spring.dataSource.support.SelfMybatisAutoConfiguration.NAME;
import static com.lj.spring.dataSource.support.SelfMybatisAutoConfiguration.NAME_DEFAULT_VALUE;


/**
 * Created by lijun on 2019/5/7
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableTransactionManagement
@ConditionalOnBean(DataSource.class)
@ConditionalOnProperty(name = NAME, havingValue = NAME_DEFAULT_VALUE)
@EnableConfigurationProperties(MultiDruidProperties.class)
@AutoConfigureAfter(value = {MultiDruidProperties.class, DataSource.class})
@Import(value = {SelfDynamicAutoConfiguration.class})
public class SelfMybatisAutoConfiguration {

    /**
     * 默认开启该配置对应的名称
     */
    static final String NAME = Prefix.DATA_SOURCE_MYBATIS_PROPERTIES + ".enable";

    /**
     * 默认开启该配置对应的名称 - 值
     */
    static final String NAME_DEFAULT_VALUE = "enable";

    /**
     * 配置 sqlSessionFactory
     */
    @Bean(name = "primarySqlSessionFactory")
    @ConditionalOnBean(DataSource.class)
    @Primary
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dynamicDataSource);
        //sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
        log.info(">>>>> 初始化【SqlSessionFactory】 完成");
        return sqlSessionFactory.getObject();
    }

    /**
     * 配置 SqlSessionTemplate
     */
    @Bean(name = "primarySqlSessionTemplate")
    @ConditionalOnBean(SqlSessionFactory.class)
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(
            @Qualifier("primarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        log.info(">>>>> 初始化【SqlSessionTemplate】 完成");
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 配置事物管理器
     */
    @Bean(name = "primaryDataSourceTransactionManager")
    @ConditionalOnBean(DataSource.class)
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManager(
            @Autowired(required = false) @Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) {
        log.info(">>>>> 初始化【DataSourceTransactionManager】 完成");
        return new DataSourceTransactionManager(dynamicDataSource);
    }

}
