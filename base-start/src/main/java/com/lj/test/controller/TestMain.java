package com.lj.test.controller;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by lijun on 2019/5/10
 */
public class TestMain {

    public static void main(String[] args) {
        final int num = 5;
        final ArrayList<User> userArrayList = Lists.newArrayList(
                new User(2, "2"),
                new User(4, "4"),
                new User(6, "6"),
                new User(8, "8"),
                new User(10, "10")
        );


        final TreeMap treeMap = new TreeMap<Integer, User>();
        final List<User> collect = userArrayList.stream()
                .sorted((u1, u2) -> u1.getSort() - num)
                .collect(Collectors.toList());
        System.out.println(collect.get(0).getName());
    }

    @Data
    @AllArgsConstructor
    static class User {
        private Integer sort;
        private String name;
    }
}
