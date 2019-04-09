package com.lj.spring.start.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lijun on 2019/4/4
 */
@RestController
@RequestMapping("")
public class TestController {

    @GetMapping
    public Object test() {
        final Integer a = Integer.valueOf("a");
        // throw new BizException(new ResultFail(22,"333",null));
        return 222;
    }
}
