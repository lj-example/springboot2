package com.lj.spring.mybatis.provider;

import com.lj.spring.mybatis.model.BaseEntityOnlyId;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lijun on 2019/5/8
 */
public class InsertCompeteProvider<T extends BaseEntityOnlyId> {

    /**
     * 生成批量插入的 sql
     */
    public String insertAll(List<T> list) {
        Class<? extends BaseEntityOnlyId> entityClass = list.get(0).getClass();
        //此处可能 出现表名 不存在
        Table annotation = entityClass.getAnnotation(Table.class);
        //获取列信息
        EntityTable entityTable = EntityHelper.getEntityTable(entityClass);

        StringBuilder sql = new StringBuilder();
        //获取全部列
        sql.append(SqlHelper.insertIntoTable(entityClass, annotation.name()));
        sql.append(insertColumn(entityTable));
        sql.append(" VALUES ");

        //生成values 后的数据模板
        ArrayList<String> allInsertData = new ArrayList<>();
        for (int index = 0; index < list.size(); index++) {
            allInsertData.add(insertValue(entityTable, index));
        }
        sql.append(String.join(",", allInsertData));
        return sql.toString();
    }

    /**
     * 获取所有需要插入的列
     */
    private static String insertColumn(EntityTable entityTable) {
        String columnStr = entityTable.getEntityClassColumns().stream()
                .filter(EntityColumn::isInsertable)
                .map(EntityColumn::getColumn)
                .collect(Collectors.joining(","));
        return "(" + columnStr + ")";
    }

    /**
     * 获取插入数据模板
     */
    private static String insertValue(EntityTable entityTable, final int index) {
        String data = entityTable.getEntityClassColumns().stream()
                .filter(EntityColumn::isInsertable)
                .map(column -> "#{arg0[" + index + "]." + column.getEntityField().getName() + "}")
                .collect(Collectors.joining(","));
        return "(" + data + ")";
    }
}
