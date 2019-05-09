package com.lj.spring.dataSource.core.dynamic;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by lijun on 2019/5/8
 */
@Slf4j
@Data
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        final String dataType = DynamicHandler.get();
        return dataType;
    }
}
