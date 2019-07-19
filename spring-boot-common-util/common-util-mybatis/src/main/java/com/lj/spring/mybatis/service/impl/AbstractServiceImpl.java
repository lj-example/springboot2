package com.lj.spring.mybatis.service.impl;


import com.lj.spring.mybatis.exception.HelperException;
import com.lj.spring.mybatis.mapper.BaseComponentMapper;
import com.lj.spring.mybatis.model.BaseEntityOnlyId;
import com.lj.spring.mybatis.util.ReflectionKit;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by junli on 2019-06-18
 */
@Slf4j
@NoArgsConstructor
public abstract class AbstractServiceImpl<T extends BaseEntityOnlyId> {

    protected Class<T> clazz;

    protected static int ERROR_RESULT_CODE = 5000000;
    protected static String RESULT_MESSAGE = "操作失败，异常数据ID=%s";

    /**
     * 生成 baseMapper
     */
    abstract Mapper<T> baseMapper();

    /**
     * 生成 baseComponentMapper
     */
    abstract BaseComponentMapper<T> baseComponentMapper();

    @Transactional(rollbackFor = SQLException.class)
    public Integer save(T t) {
        if (null == t) {
            return 0;
        }
        if (null == t.getId()) {
            buildInsertInfo(t);
            return baseMapper().insertSelective(t);
        } else if (t.getId() > 0) {
            WeekendSqls<T> sql = WeekendSqls.custom();
            sql.andEqualTo(T::getId, t.getId());
            Example example = Example.builder(currentModelClass())
                    .andWhere(sql)
                    .build();
            buildUpdateInfo(t);
            return updateByExampleSelective(t, example);
        }
        log.info("操作失败,异常数据信息 = {}", t);
        throwBizException(t.getId());
        return 0;
    }

    public T selectByPrimaryKey(Object o) {
        return baseMapper().selectByPrimaryKey(o);
    }

    public List<T> selectByExample(Example example) {
        fixExample(example);
        return baseMapper().selectByExample(example);
    }

    public T selectOne(T t) {
        return baseMapper().selectOne(t);
    }

    public int selectCount(T t) {
        return baseMapper().selectCount(t);
    }

    public List<T> selectAll() {
        return baseMapper().selectAll();
    }

    public int selectCountByExample(Example example) {
        return baseMapper().selectCountByExample(example);
    }

    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(T t) {
        checkIllegalId(t.getId());
        return baseMapper().deleteByPrimaryKey(t);
    }

    @Transactional(rollbackFor = SQLException.class)
    public int deleteByPrimaryKey(Long id) {
        return baseMapper().deleteByPrimaryKey(id);
    }

    @Transactional(rollbackFor = SQLException.class)
    public int deleteByExample(Example example) {
        return baseMapper().deleteByExample(example);
    }

    @Transactional(rollbackFor = SQLException.class)
    public int deleteByPrimaryKeys(Collection primaryKeys) {
        return baseMapper().deleteByPrimaryKey(primaryKeys);
    }

    @Transactional(rollbackFor = SQLException.class)
    public int insert(T t) {
        buildInsertInfo(t);
        return baseMapper().insert(t);
    }

    @Transactional(rollbackFor = SQLException.class)
    public int insertAll(List<T> list) {
        if (null == list || list.size() == 0) {
            return 0;
        }
        List<T> collect = list.stream()
                .map(data -> {
                    buildInsertInfo(data);
                    return data;
                })
                .collect(Collectors.toList());
        return baseComponentMapper().insertAll(collect);
    }

    @Transactional(rollbackFor = SQLException.class)
    public int updateByPrimaryKey(T t) {
        if (null == t) {
            return 0;
        }
        if (t.getId() > 0) {
            WeekendSqls<T> sql = WeekendSqls.custom();
            sql.andEqualTo(T::getId, t.getId());
            Example example = Example.builder(currentModelClass())
                    .andWhere(sql)
                    .build();
            buildUpdateInfo(t);
            return updateByExample(t, example);
        }
        log.info("操作失败,异常数据信息 = {}", t);
        throwBizException(t.getId());
        return 0;
    }

    @Transactional(rollbackFor = SQLException.class)
    public int updateByExample(T t, Example example) {
        buildUpdateInfo(t);
        return baseMapper().updateByExample(t, example);
    }

    @Transactional(rollbackFor = SQLException.class)
    public int updateByExampleSelective(T t, Example example) {
        buildUpdateInfo(t);
        return baseMapper().updateByExampleSelective(t, example);
    }

    /**
     * 判断ID 是否非法
     *
     * @return 非法 直接抛出异常信息
     */
    protected static void checkIllegalId(Object id) {
        Assert.isTrue(null != id && id instanceof Long && (Long) id > 0, String.format(RESULT_MESSAGE, id));
    }

    /**
     * 新增 时 数据补充
     */
    public void buildInsertInfo(T t) {
        t.setCreateTime(timestampInit());
        t.setModifyTime(timestampInit());
    }

    /**
     * 修改时候 数据补充
     */
    public static <T extends BaseEntityOnlyId> void buildUpdateInfo(T t) {
        t.setModifyTime(timestampInit());
    }

    /**
     * 获取当前时间
     */
    protected static Timestamp timestampInit() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static void throwBizException(Long id) {
        throw new HelperException(ERROR_RESULT_CODE, String.format(RESULT_MESSAGE, id));
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

    protected static void fixExample(Example example) {
        example.setOrderByClause(StringUtils.isEmpty(example.getOrderByClause()) ?
                "id DESC" : example.getOrderByClause() + ",id DESC");
    }

}

