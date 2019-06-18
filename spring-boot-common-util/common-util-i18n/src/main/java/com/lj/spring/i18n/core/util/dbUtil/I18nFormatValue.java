package com.lj.spring.i18n.core.util.dbUtil;

import lombok.*;

import java.util.Locale;

/**
 * Created by junli on 2019-06-17
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class I18nFormatValue {

    /**
     * 语言
     */
    private String lang;

    /**
     * 国家
     */
    private String country;

    /**
     * 对应的value
     */
    private String value;

    /**
     * 构建对象信息
     */
    public static I18nFormatValue of(String lang, String country, String value) {
        return I18nFormatValue.builder()
                .lang(lang)
                .country(country)
                .value(value)
                .build();
    }

    /**
     * 构建对象信息
     */
    public static I18nFormatValue of(Locale locale, String value) {
        return I18nFormatValue.builder()
                .lang(locale.getLanguage())
                .country(locale.getCountry())
                .value(value)
                .build();
    }
}

