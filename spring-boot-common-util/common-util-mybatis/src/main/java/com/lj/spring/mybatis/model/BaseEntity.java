package com.lj.spring.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lijun on 2019/5/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 修改者
     */
    private Long modifyId;

    /**
     * 修改时间
     */
    private Timestamp modifyTime;

    public BaseEntity(Long id, Integer status, Long creatorId, Timestamp createTime, Long modifyId, Timestamp modifyTime) {
        super(id);
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifyId = modifyId;
        this.modifyTime = modifyTime;
    }
}
