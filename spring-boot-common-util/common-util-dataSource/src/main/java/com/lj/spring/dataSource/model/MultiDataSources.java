package com.lj.spring.dataSource.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.sql.DataSource;
import java.util.Map;

/**
 * Created by lijun on 2019/4/29
 */
@Data
@AllArgsConstructor
public class MultiDataSources {

    private Map<String,DataSource> dataSources;

}
