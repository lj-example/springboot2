package com.lj.demo.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lj.demo.api.DemoApi;
import com.lj.demo.entity.dto.DemoDto;
import com.lj.demo.entity.model.Demo;
import com.lj.demo.service.DemoService;
import com.lj.spring.common.exception.BizException;
import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultFail;
import com.lj.spring.common.result.ResultSuccess;
import com.lj.spring.i18n.core.I18nHandler.I18nFolderName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.List;

/**
 * 代码示例
 * Created by junli on 2019-06-06
 */
@Slf4j
@RestController
@RequestMapping("_demo")
@RequiredArgsConstructor
@I18nFolderName("user")
public class DemoController implements DemoApi {

    private final DemoService demoService;

    /**
     * 查询所有
     */
    @GetMapping("selectAll")
    @Override
    public List<Demo> selectAll() {
        return demoService.selectAll();
    }

    /**
     * 根据名称分页查询
     */
    @GetMapping("selectPageByName")
    @Override
    public PageInfo<Demo> selectPageByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        WeekendSqls<Demo> demoWeekendSql = WeekendSqls.<Demo>custom()
                .andLike(Demo::getName, "%" + name + "%");
        Example example = Example.builder(Demo.class)
                .andWhere(demoWeekendSql)
                .build();
        return PageHelper.startPage(pageNum, pageSize)
                .doSelectPageInfo(() -> demoService.selectByExample(example));
    }

    /**
     * 自定义sql查询
     */
    @GetMapping("customizeSqlSelectByName")
    @Override
    public List<Demo> customizeSqlSelectByName(@RequestParam String name) {
        return demoService.customizeSqlSelectByName(name);
    }

    /**
     * 保存数据
     * 该示例展示了一个 void 方法如何做返回。
     */
    @PostMapping
    @Override
    public Result save(@RequestBody DemoDto demoDto) {
        Demo demo = Demo.builder()
                .demoNum(demoDto.getNum())
                .name(demoDto.getName())
                .build();
        demo.setId(demoDto.getId());
        demoService.save(demo);
        return ResultSuccess.defaultResultSuccess();
    }

    /**
     * 多数据源操作 - `read`查询
     */
    @GetMapping("selectFromReadDataSource")
    @Override
    public List<Demo> selectFromReadDataSource(String name) {
        return demoService.selectFromReadDataSource(name);
    }

    /**
     * 多数据源操作 - `write`查询
     */
    @GetMapping("selectFromWriteDataSource")
    @Override
    public List<Demo> selectFromWriteDataSource(String name) {
        return demoService.selectFromWriteDataSource(name);
    }

}

