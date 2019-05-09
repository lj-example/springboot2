package com.lj.spring.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lijun on 2019/5/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private Timestamp gmtCreate;

    /**
     * 修改者
     */
    private Long modifierId;

    /**
     * 修改时间
     */
    private Timestamp gmtModify;

}
