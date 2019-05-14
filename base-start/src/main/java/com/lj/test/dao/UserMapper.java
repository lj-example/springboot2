package com.lj.test.dao;

import com.lj.test.entity.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by lijun on 2019/4/30
 */
@Repository
public interface UserMapper extends Mapper<User> {

}
