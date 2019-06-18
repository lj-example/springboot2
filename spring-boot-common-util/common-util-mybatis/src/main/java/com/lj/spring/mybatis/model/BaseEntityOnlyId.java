package com.lj.spring.mybatis.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lijun on 2019/5/8
 */
@Data
@AllArgsConstructor
public class BaseEntityOnlyId implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 修改时间
     */
    private Timestamp modifyTime;

    /**
     * 创建时间
     */
    private Timestamp createTime;

}
