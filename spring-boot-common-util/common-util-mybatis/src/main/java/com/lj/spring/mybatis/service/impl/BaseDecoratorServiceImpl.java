package com.lj.spring.mybatis.service.impl;


import com.lj.spring.mybatis.mapper.BaseComponentMapper;
import com.lj.spring.mybatis.model.BaseEntity;
import com.lj.spring.mybatis.model.BaseStatusEnum;
import com.lj.spring.mybatis.service.BaseDecoratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by lijun on 2019/5/8
 */
@Slf4j
public class BaseDecoratorServiceImpl<T extends BaseEntity> extends AbstractServiceImpl<T> implements BaseDecoratorService<T> {

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

    @Override
    public T selectByPrimaryKey(Object key) {
        checkIllegalId(key);
        Object o = baseMapper.selectByPrimaryKey(key);
        if (null != o) {
            T result = (T) o;
            if (!BaseStatusEnum.isNormal(result.getStatus())) {
                return null;
            }
            return result;
        }
        return null;
    }

    @Override
    public T selectOne(T t) {
        t.setStatus(BaseStatusEnum.NORMAL.getCode());
        return baseMapper.selectOne(t);
    }

    @Override
    public int selectCount(T t) {
        t.setStatus(BaseStatusEnum.NORMAL.getCode());
        return baseMapper.selectCount(t);
    }

    @Override
    public List<T> selectByExample(Example example) {
        fixExample(example);
        return baseMapper.selectByExample(example);
    }

    @Override
    public int selectCountByExample(Example example) {
        fixExample(example);
        return baseMapper.selectCountByExample(example);
    }

    @Override
    public List<T> selectAll() {
        List<T> list = baseMapper.selectAll();
        List<T> collect = list.parallelStream()
                .filter(data -> Objects.nonNull(data.getStatus()))
                .filter(data -> BaseStatusEnum.isNormal(data.getStatus()))
                .collect(Collectors.toList());
        return collect;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        if (null == id || 0 >= id) {
            return 0;
        }
        WeekendSqls<T> sql = WeekendSqls.custom();
        sql.andEqualTo(T::getId, id);
        Example example = Example.builder(currentModelClass())
                .where(sql)
                .build();
        return deleteByExample(example);
    }

    @Transactional
    @Override
    public int deleteByExample(Example example) {
        try {
            T deleteEntity = currentModelClass().newInstance();
            fixExample(example);
            deleteEntity.setStatus(BaseStatusEnum.DELETED.getCode());
            buildUpdateInfo(deleteEntity);
            return baseMapper.updateByExampleSelective(deleteEntity, example);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Transactional
    @Override
    public int deleteByPrimaryKeys(Collection primaryKeys) {
        if (primaryKeys == null || primaryKeys.size() == 0) {
            log.info("批量删除的id 集合长度为0");
            return 0;
        }
        WeekendSqls<T> sql = WeekendSqls.custom();
        sql.andIn(T::getId, primaryKeys);

        Example example = Example.builder(currentModelClass())
                .where(sql)
                .build();
        fixExample(example);
        return deleteByExample(example);
    }

    @Transactional
    @Override
    public int updateByExample(T t, Example example) {
        fixExample(example);
        buildUpdateInfo(t);
        return baseMapper.updateByExample(t, example);
    }

    @Transactional
    @Override
    public int updateByExampleSelective(T t, Example example) {
        fixExample(example);
        buildUpdateInfo(t);
        return baseMapper.updateByExampleSelective(t, example);
    }

    private static void fixExample(Example example) {
        example.and().andEqualTo("status", BaseStatusEnum.NORMAL.getCode());
        example.setOrderByClause(StringUtils.isEmpty(example.getOrderByClause()) ?
                "id DESC" : example.getOrderByClause() + ",id DESC");
    }

    protected static Timestamp timestampInit() {
        return new Timestamp(System.currentTimeMillis());
    }

    @Override
    public void buildInsertInfo(T t) {
        t.setCreateTime(timestampInit());
        t.setStatus(BaseStatusEnum.NORMAL.getCode());
        t.setModifyTime(timestampInit());
    }
}
