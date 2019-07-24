package com.lj.demo.controller;

import com.lj.demo.api.I18nApi;
import com.lj.demo.entity.common.TestI18nEnum;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by junli on 2019-07-18
 */
@Slf4j
@RestController
@RequestMapping("i18n")
@RequiredArgsConstructor
@I18nFolderName("demo")
public class I18nController implements I18nApi {

    @GetMapping
    @Override
    @I18nFolderName("demo")
    public Result getMessageByKey(String key) {
        String message = I18nSourceUtil.INSTANCE.getMessage(key);
        return ResultSuccess.of(message);
    }

    /**
     * 枚举工具类测试
     */
    @GetMapping("testEnumUtil")
    @Override
    public List<HashMap<String, Object>> testEnumUtil() {
        System.out.println(TestI18nEnum.TEST_ONE.getI8nMessage());
        return I18nEnumInterface.as18nList(TestI18nEnum.class);
    }

    /**
     * @return
     */
    @GetMapping("testDBI18nUtil")
    @Override
    public String testDBI18nUtil() {
        I18nFormatValue messageChina = I18nFormatValue.of(Locale.CHINA, "信息");
        I18nFormatValue messageUS = I18nFormatValue.of(Locale.US, "message");
        String format = I18nDBUtil.INSTANCE.format(messageChina, messageUS);

        return I18nDBUtil.INSTANCE.getI18nValue(format);
    }
}

