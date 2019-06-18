package com.lj.demo.controller;


import com.github.pagehelper.PageHelper;

import com.github.pagehelper.PageInfo;
import com.lj.demo.api.DemoApi;
import com.lj.demo.entity.dto.DemoDto;
import com.lj.demo.entity.model.Demo;
import com.lj.demo.service.DemoService;
import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultSuccess;
import com.lj.spring.i18n.core.I18nHandler.I18nFolderName;
import com.lj.spring.i18n.core.util.dbUtil.I18nDBUtil;
import com.lj.spring.i18n.core.util.dbUtil.I18nFormatValue;
import com.lj.spring.i18n.core.util.enumUtil.I18nEnumInterface;
import com.lj.spring.i18n.core.util.sourceUtil.I18nSourceUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
    private final MessageSource messageSource;

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
    public List<Demo> customizeSqlSelectByName(@RequestParam String name) {
        return demoService.customizeSqlSelectByName(name);
    }

    /**
     * 保存数据
     * 该示例展示了一个 void 方法如何做返回。
     */
    @PostMapping
    @Override
    public Result save(DemoDto demoDto) {
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

    /**
     * 获取语言信息
     */
    @GetMapping("message")
    public String message() {
        return I18nSourceUtil.INSTANCE.getMessage("username");
    }

    /**
     * 枚举工具类测试
     */
    @GetMapping("testEnumUtil")
    public List<HashMap<String, Object>> testEnumUtil() {
        System.out.println(TestI18nEnum.USER.getI8nMessage());
        return I18nEnumInterface.as18nList(TestI18nEnum.class);
    }

    /**
     *
     * @return
     */
    @GetMapping("testDBI18nUtil")
    public String testDBI18nUtil() {
        I18nFormatValue messageChina = I18nFormatValue.of(Locale.CHINA, "信息");
        I18nFormatValue messageUS = I18nFormatValue.of(Locale.US, "message");
        String format = I18nDBUtil.INSTANCE.format(messageChina, messageUS);
        return I18nDBUtil.INSTANCE.getI18nValue(format);
    }

    @AllArgsConstructor
    @Getter
    public enum TestI18nEnum implements I18nEnumInterface {
        USER("username"),
        USER1("username");

        private String name;

        @Override
        public String getI18nCode() {
            return name;
        }

        @Override
        public String getI18nKey() {
            return name;
        }

    }

}

