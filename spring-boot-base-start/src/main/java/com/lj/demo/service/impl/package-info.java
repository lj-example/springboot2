/**
 * 实现类
 *
 * 1. 特别注意：不要在`serviceImpl` 中注入非本实体对应的`mapper`。
 * 2. 业务异常处理方式：throw new BizException(ResultFail.of(100,"错误信息"))。
 * 3. 多数据源 无法跨数据源事物！
 * 4. @DataSourceType("") 不要直接使用字符串方便统一维护。
 *
 * Created by junli on 2019-06-05
 */
package com.lj.demo.service.impl;