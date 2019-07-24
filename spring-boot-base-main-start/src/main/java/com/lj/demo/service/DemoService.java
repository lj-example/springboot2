package com.lj.demo.service;

import com.lj.demo.entity.model.Demo;
import com.lj.spring.mybatis.service.BaseDecoratorService;

import java.util.List;

/**
 * Created by junli on 2019-06-06
 */
public interface DemoService extends BaseDecoratorService<Demo> {

    /**
     * 自定义sql 根据 `name` 模糊查询
     */
    List<Demo> customizeSqlSelectByName(String name);

    /**
     * 多数据源操作 - `read`查询
     */
    List<Demo> selectFromReadDataSource(String name);

    /**
     * 多数据源操作 - `write`查询
     */
    List<Demo> selectFromWriteDataSource(String name);
}

