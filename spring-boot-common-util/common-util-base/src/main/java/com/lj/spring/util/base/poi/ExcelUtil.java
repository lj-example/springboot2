package com.lj.spring.util.base.poi;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lijun on 2019/4/23
 */
public class ExcelUtil<T> {

    Class<T> clazz;

    public ExcelUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 批量导入
     * 这里的批量导入指的是一个excel文件中有多个相同类型的sheet页
     */
    public List<T> importBatch(InputStream input) throws Exception {
        final HSSFWorkbook workbook = new HSSFWorkbook(input);
        List newList = Lists.newArrayList();
        int sheets;
        if (null != workbook && (sheets = workbook.getNumberOfSheets()) > 0) {
            for (int i = 0; i < sheets; i++) {
                HSSFSheet sheet = workbook.getSheetAt(i);
                if (null != sheet) {
                    List<T> importProcessor = importProcessor(sheet);
                    newList.addAll(importProcessor);
                }
            }
        }
        return newList;
    }

    /**
     * 指定sheet页导入，如果不指定默认会选第一个sheet
     */
    public List<T> importExcel(InputStream input) throws Exception {
        List<T> newList = new ArrayList<>();
        HSSFWorkbook workbook = new HSSFWorkbook(input);
        if (null != workbook) {
            int sheets = workbook.getNumberOfSheets();
            if (sheets > 0) {
                HSSFSheet sheet = workbook.getSheetAt(0);
                if (null != sheet) {
                    newList = importProcessor(sheet);
                }
            }
        }
        return newList;
    }

    /**
     * 具体处理导入
     */
    private List<T> importProcessor(HSSFSheet sheet) throws Exception {
        int maxCol = 0;
        List<T> list = new ArrayList<>();
        int rows = sheet.getPhysicalNumberOfRows();
        //有数据时才处理
        if (rows > 0) {
            List<Field> allFields = getMappedFiled(clazz, null);
            // 定义一个map用于存放列的序号和field.
            Map<Integer, Field> fieldsMap = new HashMap<>();
            for (Field field : allFields) {
                // 将有注解的field存放到map中.
                if (field.isAnnotationPresent(ExcelField.class)) {
                    ExcelField attr = field.getAnnotation(ExcelField.class);
                    int col = getExcelCol(attr.column());// 获得列号
                    maxCol = Math.max(col, maxCol);
                    field.setAccessible(true);// 设置类的私有字段属性可访问.
                    fieldsMap.put(col, field);
                }
            }
            for (int i = 1; i < rows; i++) {
                // 从第2行开始取数据,默认第一行是表头.
                HSSFRow row = sheet.getRow(i);
                int cellNum = maxCol;
                T entity = null;
                for (int j = 0; j <= cellNum; j++) {
                    HSSFCell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    int cellType = cell.getCellType();
                    String c;
                    if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                        c = String.valueOf(cell.getNumericCellValue());
                    } else if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                        c = String.valueOf(cell.getBooleanCellValue());
                    } else {
                        c = cell.getStringCellValue();
                    }
                    if (c == null || c.equals("")) {
                        continue;
                    }
                    entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
                    Field field = fieldsMap.get(j);// 从map中得到对应列的field.
                    if (field == null) {
                        continue;
                    }
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType) {
                        field.set(entity, String.valueOf(c));
                    } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                        field.set(entity, Integer.parseInt(c));
                    } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                        field.set(entity, Long.valueOf(c));
                    } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                        field.set(entity, Float.valueOf(c));
                    } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                        field.set(entity, Short.valueOf(c));
                    } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                        field.set(entity, Double.valueOf(c));
                    } else if (Character.TYPE == fieldType) {
                        if ((c != null) && (c.length() > 0)) {
                            field.set(entity, Character.valueOf(c.charAt(0)));
                        }
                    }
                }
                if (entity != null) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    /**
     * 对list数据源将其里面的数据导出到excel表单
     */
    public boolean exportExcel(List<T> lists[], String sheetNames[], OutputStream output) {
        if (lists.length != sheetNames.length) {
            return false;
        }
        HSSFWorkbook workbook = new HSSFWorkbook();// 产生工作薄对象
        for (int ii = 0; ii < lists.length; ii++) {
            List<T> list = lists[ii];
            String sheetName = sheetNames[ii];
            List<Field> fields = getMappedFiled(clazz, null);
            HSSFSheet sheet = workbook.createSheet();// 产生工作表对象
            workbook.setSheetName(ii, sheetName);
            HSSFRow row;
            HSSFCell cell;// 产生单元格
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
            row = sheet.createRow(0);// 产生一行
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                ExcelField attr = field.getAnnotation(ExcelField.class);
                int col = getExcelCol(attr.column());// 获得列号
                cell = row.createCell(col);// 创建列
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
                cell.setCellValue(attr.name());// 写入列名
                cell.setCellStyle(style);
            }
            int startNo = 0;
            int endNo = list.size();
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                T vo = list.get(i);
                for (int j = 0; j < fields.size(); j++) {
                    Field field = fields.get(j);
                    field.setAccessible(true);
                    ExcelField attr = field.getAnnotation(ExcelField.class);
                    try {
                        if (attr.isExport()) {
                            cell = row.createCell(getExcelCol(attr.column()));
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue(field.get(vo) == null ? "" : String.valueOf(field.get(vo)));
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            output.flush();
            workbook.write(output);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 对list数据源将其里面的数据导出到excel表单
     */
    public boolean exportExcel(List<T> list, String sheetName, OutputStream output) {
        //sheet表格
        List<T>[] lists = new ArrayList[1];
        lists[0] = list;
        //sheet名称
        String[] sheetNames = new String[1];
        sheetNames[0] = sheetName;
        return exportExcel(lists, sheetNames, output);
    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3,4
     *
     * @param col
     */
    public static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 得到实体类所有通过注解映射了数据表的字段
     * <p>
     * 递归调用
     */
    private List<Field> getMappedFiled(Class clazz, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        Field[] allFields = clazz.getDeclaredFields();
        // 得到所有定义字段
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelField.class)) {
                fields.add(field);
            }
        }
        // 继承父类所有定义字段
        if (clazz.getSuperclass() != null
                && !clazz.getSuperclass().equals(Object.class)) {
            getMappedFiled(clazz.getSuperclass(), fields);
        }
        return fields;
    }

}

