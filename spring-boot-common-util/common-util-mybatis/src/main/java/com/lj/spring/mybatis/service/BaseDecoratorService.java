package com.lj.spring.mybatis.service;

import com.lj.spring.mybatis.model.BaseEntity;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;

/**
 * Created by lijun on 2019/5/8
 */
public interface BaseDecoratorService<T extends BaseEntity> {

    /**
     * 存储数据 - 不会处理 null 情况，默认使用数据库的默认值
     * 如果执行的新增操作，该方法调用的是 insertSelective
     * 如果有其他需求可以手动调用 insert
     * 如果执行的修改操作，该方法调用的是 updateByPrimaryKeySelective
     * 如果有其他需求可以手动调用 updateByExample
     *
     * @param t
     * @return
     */
    Integer save(T t);

    /**
     * 根据主键查询
     *
     * @param o 主键，目前系统中全为 long
     * @return
     */
    T selectByPrimaryKey(Object o);

    /**
     * 根据构建条件查询
     *
     * @param example 查询条件
     * @return 查询结果集
     */
    List<T> selectByExample(Example example);

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     *
     * @param t 查询对象
     */
    T selectOne(T t);

    /**
     * 根据实体中的属性查询总数，查询条件使用等号
     *
     * @param t 查询条件
     * @return
     */
    int selectCount(T t);


    /**
     * 查询所有数据
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 根据构建条件统计数量
     *
     * @param example 查询条件
     * @return 总数
     */
    int selectCountByExample(Example example);


    /**
     * 需要传递对象， 不能之传入id
     * 根据主键删除数据（逻辑删除）
     *
     * @param t 主键，目前系统中全为 long
     * @return
     */
    int deleteByPrimaryKey(T t);

    /**
     * 根据id 删除
     * 根据主键删除数据（逻辑删除）
     *
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 根据构建条件删除数据（逻辑删除）
     *
     * @param example 删除条件
     * @return 操作成功数量
     */
    int deleteByExample(Example example);

    /**
     * 根据主键批量删除
     *
     * @param primaryKeys 主键合集
     * @return
     */
    int deleteByPrimaryKeys(Collection primaryKeys);

    /**
     * 数据插入
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 批量插入 空数据会插入 null
     * @param list 需要批量插入的数据
     * @return
     */
    int insertAll(List<T> list);

    /**
     * 根据ID 全字段更新 - null 也会被更新
     *
     * @param t 需要更新的数据
     * @return
     */
    int updateByPrimaryKey(T t);

    /**
     * 根据Example条件更新实体`t`包含的全部属性，null值会被更新
     *
     * @param example 修改条件
     * @return 操作成功数量
     */
    int updateByExample(T t, Example example);

    /**
     * 根据Example条件更新实体`t`非null 属性
     *
     * @param example 修改条件
     * @return 操作成功数量
     */
    int updateByExampleSelective(T t, Example example);
}
