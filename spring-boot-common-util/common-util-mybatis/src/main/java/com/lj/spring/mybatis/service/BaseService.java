package com.lj.spring.mybatis.service;


import com.lj.spring.mybatis.model.BaseEntityOnlyId;

/**
 * Created by lijun on 2019/5/8
 */
public interface BaseService<T extends BaseEntityOnlyId> extends AbstractService<T> {

}
