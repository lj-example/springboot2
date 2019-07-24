/**
 * 所有service 位置
 * 二级目录为 业务目录，禁止直接在该目录下写对象！！！！
 * 该目录下所有 对象必须以`Service`结尾
 *
 * 1. 接口中非`default` 方法都是 public 的，不需要声明`public`。
 * 2. 根据实体类型决定继承哪个`service`
 *   - `BaseDecoratorService<T>` 该实现中 所有的 `delete`方法全被改写成了`update`即逻辑删除。
 *   - `BaseService<T>` 未做任何改变，提供了`save`方法。
 *
 * Created by lijun on 2019/6/4
 */
package com.lj.demo.service;