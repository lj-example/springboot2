package com.lj.spring.i18n.core.util.dbUtil;

import com.alibaba.fastjson.JSON;
import com.lj.spring.i18n.core.util.UtilInterface;
import lombok.NonNull;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;


/**
 * Created by junli on 2019-06-17
 */
public enum I18nDBUtil implements UtilInterface {

    INSTANCE;

    /**
     * 格式化
     */
    public String format(I18nFormatValue... args) {
        if (Objects.isNull(args)) {
            return "";
        }
        return JSON.toJSONString(args);
    }

    /**
     * F
     * 获取对应的语言信息
     */
    public String getI18nValue(@NonNull String baseValue) {
        List<I18nFormatValue> i18nFormatValues = JSON.parseArray(baseValue, I18nFormatValue.class);
        Locale locale = UtilInterface.getLocaleAndAssertNull();
        Optional<I18nFormatValue> formatValue = i18nFormatValues.stream()
                .filter(i18nFormatValue ->
                        locale.getCountry().equals(i18nFormatValue.getCountry())
                                && locale.getLanguage().equals(i18nFormatValue.getLang())
                )
                .findAny();
        if (formatValue.isPresent()) {
            return formatValue.get().getValue();
        }
        return "";
    }
}

