package com.lj.spring.mybatis.mapper;

import com.lj.spring.mybatis.model.BaseEntityOnlyId;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by lijun on 2019/5/8
 */
@Mapper
public interface BaseComponentMapper<T extends BaseEntityOnlyId> extends
        InsertComponentMapper<T> {
}
