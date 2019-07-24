package com.lj.demo.api;


import com.github.pagehelper.PageInfo;
import com.lj.demo.entity.dto.DemoDto;
import com.lj.demo.entity.model.Demo;
import com.lj.spring.common.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * Created by junli on 2019-06-06
 */
@Api(value = "项目示例", tags = "项目示例")
public interface DemoApi {

    @ApiOperation(value = "查询所有数据示例")
    List<Demo> selectAll();

    @ApiOperation(value = "根据名称分页查询数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "查询名称", dataTypeClass = String.class),
            @ApiImplicitParam(name = "pageNum", value = "页号", dataTypeClass = Integer.class),
            @ApiImplicitParam(name = "pageSize", value = "页面大小", dataTypeClass = Integer.class)
    })
    PageInfo<Demo> selectPageByName(String name, Integer page, Integer pageSize);


    @ApiOperation(value = "自定义 sql 查询")
    @ApiImplicitParam(name = "name", value = "查询名称", dataTypeClass = String.class)
    List<Demo> customizeSqlSelectByName(String name);

    @ApiOperation(value = "保存信息")
    Result save(DemoDto demoDto);


    @ApiOperation(value = "多数据源操作-`read`查询")
    @ApiImplicitParam(name = "name", value = "查询名称", dataTypeClass = String.class)
    List<Demo> selectFromReadDataSource(String name);


    @ApiOperation(value = "多数据源操作-`write`查询")
    @ApiImplicitParam(name = "name", value = "查询名称", dataTypeClass = String.class)
    List<Demo> selectFromWriteDataSource(String name);
}

