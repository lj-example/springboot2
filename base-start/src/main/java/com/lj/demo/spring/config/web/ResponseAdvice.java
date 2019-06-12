package com.lj.demo.spring.config.web;

import com.google.common.collect.Lists;
import com.lj.spring.common.web.ResponseAdviceTemplate;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 定义需要 返回结果需要被封装的 包路径 建议直接返回`controller`路径
 * Created by junli on 2019-06-10
 */
@RestControllerAdvice
public class ResponseAdvice extends ResponseAdviceTemplate {

    private static final List<String> SUPPORT_PATH = Lists.newArrayList("com.lj.demo.controller");

    @Override
    public List<String> supportPath() {
        return SUPPORT_PATH;
    }
}

