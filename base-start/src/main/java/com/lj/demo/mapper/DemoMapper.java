package com.lj.demo.mapper;


import com.lj.demo.entity.model.Demo;
import com.lj.demo.mapper.sqlProvider.DemoSqlProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by junli on 2019-06-06
 */
@Repository
public interface DemoMapper extends Mapper<Demo> {

    /**
     * 自定义sql 根据 `name` 模糊查询
     */
    @SelectProvider(type = DemoSqlProvider.class, method = "customizeSqlSelectByName")
    List<Demo> customizeSqlSelectByName(@Param("name") String name);
}

