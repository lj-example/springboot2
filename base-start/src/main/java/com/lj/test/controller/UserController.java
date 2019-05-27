package com.lj.test.controller;

import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultSuccess;
import com.lj.test.entity.User;
import com.lj.test.service.UserService;
import com.lj.test.service.UserTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by lijun on 2019/4/30
 */
@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;
    final UserTestService userTestService;

    @GetMapping()
    public List<User> get() {
        return userService.selectAll();
    }


    @PostMapping
    public Result test(@RequestParam Long id,@RequestParam String name, @RequestParam Integer age) {
        final User user = new User();
        user.setName(name);
        user.setAge(age);
        userTestService.save(user);

        final User user1 = new User();

        user1.setName(name);
        user1.setAge(age);

        userTestService.save2(user1);
        final List<User> users = userService.selectAll();
        System.out.println(users.size());
        return ResultSuccess.defaultResultSuccess();
    }

    @PostMapping("/2")
    public Result test2(@RequestParam String name, @RequestParam Integer age) {
        final User user = new User();
        user.setName(name);
        user.setAge(age);
        userTestService.save2(user);
        return ResultSuccess.defaultResultSuccess();
    }
}
