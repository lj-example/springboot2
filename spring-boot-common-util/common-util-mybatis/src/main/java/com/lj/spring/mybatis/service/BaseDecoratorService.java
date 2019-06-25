package com.lj.spring.mybatis.service;

import com.lj.spring.mybatis.model.BaseEntity;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;

/**
 * Created by lijun on 2019/5/8
 */
public interface BaseDecoratorService<T extends BaseEntity> extends AbstractService<T> {

    /**
     * 需要传递对象， 不能之传入id
     * 根据主键删除数据（逻辑删除）
     *
     * @param t 主键，目前系统中全为 long
     * @return
     */
    @Override
    int deleteByPrimaryKey(T t);

    /**
     * 根据id 删除
     * 根据主键删除数据（逻辑删除）
     *
     * @return
     */
    @Override
    int deleteByPrimaryKey(Long id);

    /**
     * 根据构建条件删除数据（逻辑删除）
     *
     * @param example 删除条件
     * @return 操作成功数量
     */
    @Override
    int deleteByExample(Example example);

    /**
     * 根据主键批量删除
     *
     * @param primaryKeys 主键合集
     * @return
     */
    @Override
    int deleteByPrimaryKeys(Collection primaryKeys);

}
