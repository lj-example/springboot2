package com.lj.spring.dataSource.support;


import com.lj.spring.dataSource.common.Common;
import com.lj.spring.dataSource.common.Prefix;
import com.lj.spring.dataSource.config.DruidDataSourceProperties;
import com.lj.spring.dataSource.config.MultiDruidProperties;
import com.lj.spring.dataSource.core.DruidDataSourceFactory;
import com.lj.spring.dataSource.model.MultiDataSources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static com.lj.spring.dataSource.support.SelfDruidAutoConfiguration.NAME;
import static com.lj.spring.dataSource.support.SelfDruidAutoConfiguration.NAME_DEFAULT_VALUE;

/**
 * Created by lijun on 2019/4/29
 */
@Configuration
@ConditionalOnProperty(name = NAME, havingValue = NAME_DEFAULT_VALUE)
@EnableConfigurationProperties(MultiDruidProperties.class)
@AutoConfigureAfter(SelfDruidMonitorConfiguration.class)
public class SelfDruidAutoConfiguration {
    /**
     * 默认开启该配置对应的名称
     */
    static final String NAME = Prefix.DATA_SOURCE_PROPERTIES + ".autoDataSource";

    /**
     * 默认开启该配置对应的名称 - 值
     */
    static final String NAME_DEFAULT_VALUE = "enable";

    @Slf4j
    @RequiredArgsConstructor
    @Configuration
    @AutoConfigureBefore(DataSourceAutoConfiguration.class)
    protected static class DruidDataSourceConfiguration {

        private final MultiDruidProperties druidProperties;

        @Bean(name = Common.MASTER_DATA_SOURCE_NAME)
        @Primary
        public DataSource dataSource() throws SQLException {
            final DataSource dataSource = DruidDataSourceFactory.createDataSource(druidProperties);
            log.info(">>>>> 初始化数据库连接（master）：" + druidProperties.getUrl());
            return dataSource;
        }
    }

    @RequiredArgsConstructor
    @Configuration
    protected static class DruidMultiDataSourcesConfiguration {

        private final MultiDruidProperties multiDruidProperties;

        @Bean(name = Common.MULTI_DATA_SOURCE_NAME)
        public MultiDataSources dataSourcesRegister() {
            Map<String, DataSource> dataSourcesMap = new HashMap<>();
            if (multiDruidProperties == null
                    || multiDruidProperties.getDynamicDataSource() == null
                    || multiDruidProperties.getDynamicDataSource().size() == 0) {
                return new MultiDataSources(dataSourcesMap);
            }
            Map<String, DruidDataSourceProperties> dataSources = multiDruidProperties.getDynamicDataSource();
            dataSources.forEach((key, value) -> {
                combine(multiDruidProperties, value);
                try {
                    dataSourcesMap.put(key, DruidDataSourceFactory.createDataSource(value));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            return new MultiDataSources(dataSourcesMap);
        }

        //属性合并
        private static void combine(Object source, Object target) {
            if (source == null || target == null) {
                return;
            }
            Assert.state(target.getClass().isAssignableFrom(source.getClass()), "类型不一致");
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());
                PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                for (PropertyDescriptor descriptor : descriptors) {
                    Method readMethod = descriptor.getReadMethod();
                    Method writeMethod = descriptor.getWriteMethod();
                    //只设置为null的
                    if (readMethod.invoke(target) == null) {
                        Object value = readMethod.invoke(source);
                        if (value != null) {
                            writeMethod.invoke(target, value);
                        }
                    }
                }
            } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}