package com.lj.demo.mapper.sqlProvider;

import com.lj.demo.entity.model.Demo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import javax.persistence.Table;

/**
 * 示例地址：
 *  - https://www.programcreek.com/java-api-examples/index.php?api=org.apache.ibatis.jdbc.SQL
 *  - http://www.mybatis.org/mybatis-3/apidocs/reference/org/apache/ibatis/jdbc/SQL.html
 * Created by junli on 2019-06-11
 */
public class DemoSqlProvider {

    /**
     * 自定义sql 根据 `name` 模糊查询
     */
    public String customizeSqlSelectByName(@Param("name") final String name) {
        String table = SqlHelper.getDynamicTableName(Demo.class, Demo.class.getAnnotation(Table.class).name());
        String allColumns = SqlHelper.getAllColumns(Demo.class);
        return new SQL() {{
            SELECT(allColumns);
            FROM(table);
            WHERE("demo_name like CONCAT('%',#{name},'%')");
        }}.toString();
    }

}

