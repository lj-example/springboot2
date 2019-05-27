package com.lj.test.service.impl;

import com.lj.spring.dataSource.core.dynamic.DataSourceType;
import com.lj.test.config.dataSource.DataSourceName;
import com.lj.test.entity.User;
import com.lj.test.service.UserService;
import com.lj.test.service.UserTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by lijun on 2019/5/8
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserTestServiceImpl implements UserTestService {

    final UserService userService;

    @Override
    @DataSourceType(DataSourceName.READ)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save(User user) {
        userService.save(user);
        //Integer.valueOf("aaa");
    }

    @Override
    @DataSourceType(DataSourceName.WRITE)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void save2(User user) {
        userService.save(user);
      //  Integer.valueOf("aaa");
    }

}
