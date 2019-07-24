package com.lj.demo.controller;

import com.lj.demo.api.UserTokenApi;
import com.lj.spring.common.head.HeadCommon;
import com.lj.spring.common.result.Result;
import com.lj.spring.common.result.ResultFail;
import com.lj.spring.common.result.ResultSuccess;
import com.lj.spring.web.user.annotation.UserToken;
import com.lj.spring.web.user.common.Common;
import com.lj.spring.web.user.model.UserBusinessBo;
import com.lj.spring.web.user.model.UserSession;
import com.lj.spring.web.user.service.login.UserLoginSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by junli on 2019-07-23
 */
@Slf4j
@RestController
@RequestMapping("login")
@RequiredArgsConstructor
public class UserTokenController implements UserTokenApi {

    private final UserLoginSessionService userLoginSessionService;

    @Override
    @PostMapping("in")
    public Result login() {
        UserBusinessBo businessBo = UserBusinessBo.builder()
                .userId(10001L)
                .mobile("18211112222")
                .build();
        String token = userLoginSessionService.buildUserTokenAfterLogin(businessBo);
        return ResultSuccess.of(token);
    }

    @Override
    @PostMapping("out")
    public Result loginOut(
            @RequestHeader(HeadCommon.USER_TOKEN) String token,
            @UserToken UserSession userSession) {
        String result = userSession.getMobile() + ":" + userSession.getUserId();
        userLoginSessionService.cleanUserTokenAfterLoginOut(token);
        return ResultSuccess.of(result);
    }
}

