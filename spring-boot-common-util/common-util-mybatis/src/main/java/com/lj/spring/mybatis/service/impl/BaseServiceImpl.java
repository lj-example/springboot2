package com.lj.spring.mybatis.service.impl;

import com.lj.spring.mybatis.mapper.BaseComponentMapper;
import com.lj.spring.mybatis.model.BaseEntityOnlyId;
import com.lj.spring.mybatis.service.BaseService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

/**
 * Created by lijun on 2019/5/8
 */
@NoArgsConstructor
public class BaseServiceImpl<T extends BaseEntityOnlyId> extends AbstractServiceImpl<T> implements BaseService<T> {

    @Autowired
    private Mapper<T> baseMapper;

    @Autowired
    private BaseComponentMapper<T> baseComponentMapper;

    @Override
    Mapper<T> baseMapper() {
        return baseMapper;
    }

    @Override
    BaseComponentMapper<T> baseComponentMapper() {
        return baseComponentMapper;
    }

}
