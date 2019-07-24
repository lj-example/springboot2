package com.lj.demo.entity.common;

import com.lj.spring.i18n.core.util.enumUtil.I18nEnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by junli on 2019-07-19
 */
@AllArgsConstructor
@Getter
public enum TestI18nEnum implements I18nEnumInterface {
    TEST_ONE("testOne"),
    TEST_TWO("testTwo");

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

