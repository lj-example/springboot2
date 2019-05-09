package com.lj.spring.mybatis.mapper;


import com.lj.spring.mybatis.model.BaseEntityOnlyId;
import com.lj.spring.mybatis.provider.InsertCompeteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;



/**
 * Created by lijun on 2019/5/8
 */
@Mapper
public interface InsertComponentMapper<T extends BaseEntityOnlyId> {

    /**
     * 批量插入
     */
    @InsertProvider(type = InsertCompeteProvider.class, method = "insertAll")
    int insertAll(@Param("arg0") List<T> list);
}
