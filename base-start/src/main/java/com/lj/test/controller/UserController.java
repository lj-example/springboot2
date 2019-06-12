package com.lj.test.controller;

import com.github.pagehelper.ISelect;
import com.github.pagehelper.PageHelper;

import com.lj.test.dao.UserMapper;
import com.lj.test.entity.User;
import com.lj.test.service.UserService;
import com.lj.test.service.UserTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by lijun on 2019/4/30
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Api("用户信息")
public class UserController {

    final UserService userService;
    final UserTestService userTestService;

    final UserMapper userMapper;

    @GetMapping()
    @ApiOperation(value="获取所有信息", notes="..")
    public Object get() {
        //throw new BizException("CESHI");
        //return new Date();
        return PageHelper.startPage(1,2)
                .doSelectPageInfo(() ->
                         userService.selectAll()
                    );
    }

    @GetMapping("/all")
    public Object getAll() {
        //throw new BizException("CESHI");
        //return new Date();
        return userMapper.selectAll();
    }

    @PostMapping
    public Object test(@RequestParam Long id, @RequestParam String name, @RequestParam Integer age) {
        final User user = new User();
        user.setName(name);
        user.setAge(age);
        userMapper.insert(user);
//        final User user1 = new User();
//
//        user1.setName(name);
//        user1.setAge(age);
//
//        userTestService.save2(user1);
//        final List<User> users = userMapper.selectAll();
//        System.out.println(users.size());

        return null;
    }

    @PostMapping("/2")
    public String test2(@RequestParam String name, @RequestParam Integer age) {
        final User user = new User();
        user.setName(name);
        user.setAge(age);
        userTestService.save2(user);
        return null;
    }



}
