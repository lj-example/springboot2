package com.lj.test.entity;

import com.lj.spring.mybatis.model.BaseEntityOnlyId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * Created by lijun on 2019/4/30
 */
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntityOnlyId {

    private String name;

    private Integer age;

}
