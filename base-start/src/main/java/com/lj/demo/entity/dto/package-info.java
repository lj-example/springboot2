/**
 * DTO
 * 强制使用 example 属性，或者声明非 `String` 的数据类型。
 * 特别注意：swagger-ui 在初始化的时回写默认值，如果当前的数据类型是非`String`，`example` 的默认值是'',
 * 执行 valueOf 可能会出异常，但不会影响系统运行。
 *
 * Created by lijun on 2019/6/4
 */
package com.lj.demo.entity.dto;