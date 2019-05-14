package com.lj.test.service.impl;

import com.lj.test.dao.UserMapper;
import com.lj.test.entity.User;
import com.lj.spring.mybatis.service.impl.BaseServiceImpl;
import com.lj.test.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijun on 2019/4/30
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService  {

    final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        super(User.class);
        this.userMapper = userMapper;
    }

    @Override
    public List<User> selectAll() {
       return super.selectAll();
    }

}
