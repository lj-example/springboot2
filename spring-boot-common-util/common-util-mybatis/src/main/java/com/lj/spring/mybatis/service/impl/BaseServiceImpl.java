package com.lj.spring.mybatis.service.impl;


import com.lj.spring.mybatis.mapper.BaseComponentMapper;
import com.lj.spring.mybatis.model.BaseEntityOnlyId;
import com.lj.spring.mybatis.service.BaseService;
import com.lj.spring.mybatis.util.ReflectionKit;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.util.Collection;
import java.util.List;

/**
 * Created by lijun on 2019/5/8
 */
@NoArgsConstructor
public class BaseServiceImpl<T extends BaseEntityOnlyId> implements BaseService<T> {

    @Autowired
    private Mapper<T> baseMapper;

    @Autowired
    private BaseComponentMapper<T> baseComponentMapper;

    private Class<T> clazz;

    @Transactional
    @Override
    public Integer save(T t) {
        if (null == t) {
            return 0;
        }
        if (null == t.getId()) {
            return baseMapper.insertSelective(t);
        } else if (t.getId() > 0) {
            WeekendSqls<T> sql = WeekendSqls.custom();
            sql.andEqualTo(T::getId, t.getId());
            Example example = Example.builder(currentModelClass())
                    .andWhere(sql)
                    .build();
            return updateByExampleSelective(t, example);
        }
        return 0;
    }

    @Transactional
    @Override
    public T selectByPrimaryKey(Object o) {
        return baseMapper.selectByPrimaryKey(o);
    }

    @Transactional
    @Override
    public List<T> selectByExample(Example example) {
        return baseMapper.selectByExample(example);
    }

    @Transactional
    @Override
    public T selectOne(T t) {
        return baseMapper.selectOne(t);
    }

    @Transactional
    @Override
    public int selectCount(T t) {
        return baseMapper.selectCount(t);
    }

    @Transactional
    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    @Transactional
    @Override
    public int selectCountByExample(Example example) {
        return baseMapper.selectCountByExample(example);
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(T t) {
        return baseMapper.deleteByPrimaryKey(t);
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        return baseMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    @Override
    public int deleteByExample(Example example) {
        return baseMapper.deleteByExample(example);
    }

    @Transactional
    @Override
    public int deleteByPrimaryKeys(Collection primaryKeys) {
        return baseMapper.deleteByPrimaryKey(primaryKeys);
    }

    @Transactional
    @Override
    public int insert(T t) {
        return baseMapper.insert(t);
    }

    @Transactional
    @Override
    public int insertAll(List<T> list) {
        return baseComponentMapper.insertAll(list);
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(T t) {
        return baseMapper.updateByPrimaryKey(t);
    }

    @Transactional
    @Override
    public int updateByExample(T t, Example example) {
        return baseMapper.updateByExample(t, example);
    }

    @Transactional
    @Override
    public int updateByExampleSelective(T t, Example example) {
        return baseMapper.updateByExampleSelective(t, example);
    }

    /**
     * 获取当前泛型参数
     */
    protected Class<T> currentModelClass() {
        if (null == clazz) {
            clazz = ReflectionKit.getSuperClassGenericType(getClass(), 0);
        }
        return clazz;
    }

}
