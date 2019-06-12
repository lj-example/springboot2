package com.lj.demo.service.impl;


import com.lj.demo.entity.model.Demo;
import com.lj.demo.mapper.DemoMapper;
import com.lj.demo.service.DemoService;
import com.lj.demo.spring.config.dataSource.DataSourceName;
import com.lj.spring.dataSource.core.dynamic.DataSourceType;
import com.lj.spring.mybatis.service.impl.BaseDecoratorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by junli on 2019-06-06
 */
@Service
@RequiredArgsConstructor
public class DemoServiceImpl extends BaseDecoratorServiceImpl<Demo> implements DemoService {

    private final DemoMapper demoMapper;

    @Override
    public List<Demo> customizeSqlSelectByName(String name) {
        return demoMapper.customizeSqlSelectByName(name);
    }

    @Override
    @DataSourceType(DataSourceName.READ)
    public List<Demo> selectFromReadDataSource(String name) {
        return demoMapper.customizeSqlSelectByName(name);
    }

    @Override
    @DataSourceType(DataSourceName.WRITE)
    public List<Demo> selectFromWriteDataSource(String name) {
        return demoMapper.customizeSqlSelectByName(name);
    }
}

