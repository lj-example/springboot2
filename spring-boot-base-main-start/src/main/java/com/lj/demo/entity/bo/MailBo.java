package com.lj.demo.entity.bo;

import com.lj.spring.util.base.poi.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by junli on 2019-07-22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailBo {

    @ExcelField(name = "名称", column = "A")
    private String name;

    @ExcelField(name = "年龄", column = "B")
    private Integer age;

}

