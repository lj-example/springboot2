package com.lj.spring.util.base;

import com.google.common.collect.Lists;
import com.lj.spring.util.base.poi.ExcelField;
import com.lj.spring.util.base.poi.ExcelUtil;
import lombok.Builder;
import lombok.Data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by lijun on 2019/4/24
 */
public class TestMain {
    public static void main(String[] args) throws IOException {
        ArrayList<User> list = Lists.newArrayList();
        for (int index = 0; index < 10; index++) {
            User user = User.builder()
                    .id(index)
                    .name("name  " + index)
                    .age(index + 10)
                    .build();
            list.add(user);
        }

        ExcelUtil<User> userExcelUtil = new ExcelUtil<>(User.class);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(2048)) {
            userExcelUtil.exportExcel(list, "测试", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Data
    @Builder
    public static class User {

        @ExcelField(name = "id", column = "A")
        private Integer id;

        @ExcelField(name = "name", column = "B")
        private String name;

        @ExcelField(name = "age", column = "C")
        private Integer age;

    }
}
