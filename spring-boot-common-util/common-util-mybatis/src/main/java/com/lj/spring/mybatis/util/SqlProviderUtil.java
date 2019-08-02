package com.lj.spring.mybatis.util;

import lombok.experimental.UtilityClass;
import tk.mybatis.mapper.util.StringUtil;
import tk.mybatis.mapper.weekend.Fn;
import tk.mybatis.mapper.weekend.reflection.Reflections;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by junli on 2019-07-24
 */
@UtilityClass
public class SqlProviderUtil {

    /**
     * 生成inSql 语句
     */
    public static String buildInSql(Collection collection) {
        Object sqlBody = collection.stream()
                .filter(Objects::nonNull)
                .map(String::valueOf)
                .map(value -> "'" + value + "'")
                .collect(Collectors.joining(","));
        return "(" + sqlBody.toString() + ")";
    }

    /**
     * 通过lambda 获取字段名
     * 该方法用于强制擦除泛型
     */
    public static <T> String getFieldByLambda(Fn<T, Object> fn) {
        return Reflections.fnToFieldName(fn);
    }

    /**
     * 通过lambda 获取列名
     * 该方法用于强制擦除泛型
     */
    public static <T> String getColumnByLambda(Fn<T, Object> fn) {
        String fieldName = Reflections.fnToFieldName(fn);
        return StringUtil.camelhumpToUnderline(fieldName);
    }

}

