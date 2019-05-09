package com.lj.spring.dataSource.support;


import com.lj.spring.dataSource.config.MultiDruidProperties;
import com.lj.spring.dataSource.core.dynamic.DynamicCommon;
import com.lj.spring.dataSource.core.dynamic.DynamicDataSource;
import com.lj.spring.dataSource.model.MultiDataSources;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;



/**
 * Created by lijun on 2019/5/8
 */
@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(MultiDruidProperties.class)
@AutoConfigureAfter(value = {DataSource.class, MultiDataSources.class})
public class SelfDynamicAutoConfiguration {

    /**
     * 动态数据源
     */
    @Bean(name = "dynamicDataSource")
    @ConditionalOnBean(value = {DataSource.class, MultiDataSources.class})
    public DynamicDataSource dynamicDataSource(DataSource dataSource, MultiDataSources multiDataSources) {
        final DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setDefaultTargetDataSource(dataSource);
        if (Objects.nonNull(multiDataSources) && Objects.nonNull(multiDataSources.getDataSources())) {
            final Map<Object, Object> targetData = multiDataSources.getDataSources().entrySet()
                    .stream()
                    .filter(entry -> Objects.nonNull(entry.getValue()))
                    .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue));
            //把主数据库 作为 master 添加进选项中
            targetData.put(DynamicCommon.MASTER_NAME,dataSource);
            //生成最终的数据库选择列表
            dynamicDataSource.setTargetDataSources(targetData);
        }
        return dynamicDataSource;
    }

}
