/**
 * 数据库实体
 * 1. 强制声明`@EqualsAndHashCode(callSuper = true)`
 * 2. `@Table` 可以指定数据库 表名称，`@Column` 可以指定数据库中对应字段名称，默认是 驼峰转下划线。
 * 3. 根据当前数据表格式决定是继承 `BaseEntity`或`BaseEntityOnlyId`.
 * 4. 显示声明 `AllArgsConstructor` 并标注`@Builder`可以支持全属性的`build`，否则只能支持当前类中声明的属性，
 *    该问题为`lombok`组件支持问题，待后续组件更新处理。
 *
 * 5. 特别注意：不要出现`isXX` 的字段！！！
 * 6. 特别注意：所有的状态值，最好都提供枚举，不要在接口传递中出现魔法数字、魔法字符！
 *
 * Created by lijun on 2019/6/4
 */
package com.lj.demo.entity.model;