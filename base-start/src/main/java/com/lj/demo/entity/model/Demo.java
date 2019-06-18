package com.lj.demo.entity.model;

import com.lj.spring.mybatis.model.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by junli on 2019-06-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "demo")
public class Demo extends BaseEntity {

    /**
     * 测试 - 名称 当数据库字段与实体字段不一致时候
     */
    @Column(name = "demo_name")
    private String name;

    /**
     * 示例 - 字符 当数据库字段与实体字段一致
     */
    private Integer demoNum;

    @Builder
    public Demo(Long id, Timestamp modifyTime, Timestamp createTime, Integer status, Long creatorId, Long modifyId, String name, Integer demoNum) {
        super(id, modifyTime, createTime, status, creatorId, modifyId);
        this.name = name;
        this.demoNum = demoNum;
    }
}

