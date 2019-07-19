/**
 * 所有 自定义sql 文件位置
 * 该目录下所有对象建议以`SqlProvider`结尾。
 *
 * 1. 该操作中返回的 SQL 理论和`xml`中一直，再交由`mybatis`解析器进行解析。
 * 2. 该目录中的 SQL 务必要考虑防 sql 注入。
 * 3. 不建议直接把参数拼接在 sql 中，参数参数模板的方式最好。
 * 4. 不建议直接使用`string` 拼接 sql,使用 SQL() 类拼接sql具有较高的阅读性！
 *
 * 示例地址：
 *  - https://www.programcreek.com/java-api-examples/index.php?api=org.apache.ibatis.jdbc.SQL
 *  - http://www.mybatis.org/mybatis-3/apidocs/reference/org/apache/ibatis/jdbc/SQL.html
 *
 * Created by junli on 2019-06-06
 */
package com.lj.demo.mapper.sqlProvider;