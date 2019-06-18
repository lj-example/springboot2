package com.lj.spring.mybatis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lijun on 2019/5/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseEntity extends BaseEntityOnlyId implements Serializable {

    /**
     * 逻辑状态 - 数据状态
     */
    private Integer status;

    /**
     * 创建者
     */
    private Long creatorId;

    /**
     * 修改者
     */
    private Long modifyId;

    public BaseEntity(Long id, Timestamp modifyTime, Timestamp createTime, Integer status, Long creatorId, Long modifyId) {
        super(id, modifyTime, createTime);
        this.status = status;
        this.creatorId = creatorId;
        this.modifyId = modifyId;
    }
}
