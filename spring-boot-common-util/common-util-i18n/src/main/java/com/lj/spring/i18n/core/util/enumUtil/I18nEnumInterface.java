package com.lj.spring.i18n.core.util.enumUtil;

import com.lj.spring.i18n.core.util.sourceUtil.I18nSourceUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by junli on 2019-06-17
 */
public interface I18nEnumInterface {

    /**
     * 返回 多语言配置文件中的配置信息
     */
    String getI18nCode();

    /**
     * 返回 key,用以返回集合时用作 map 的key
     *
     * @return
     */
    String getI18nKey();

    /**
     * 返回当前枚举的排序下标
     */
    default int getI18nIndex() {
        return 0;
    }

    /**
     * 转换成对应语言的 List
     *
     * @return 对应语言的 List
     */
    static <T extends I18nEnumInterface> List<HashMap<String, Object>> as18nList(Class<T> classz) {
        if (Objects.nonNull(classz)) {
            List<HashMap<String, Object>> mapList = Arrays.stream(classz.getEnumConstants())
                    .sorted(Comparator.comparingInt(I18nEnumInterface::getI18nIndex))
                    .map(constant -> {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("key", constant.getI18nKey());
                        map.put("value", constant.getI8nMessage());
                        return map;
                    })
                    .collect(Collectors.toList());
            return mapList;
        }
        return null;
    }

    /**
     * 获取当前的 message 信息
     *
     * @return 对应的语言信息
     */
    default String getI8nMessage() {
        return I18nSourceUtil.INSTANCE.getMessage(getI18nCode());
    }

}

