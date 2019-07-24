package com.lj.demo.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by junli on 2019-06-06
 */
@ApiModel("示例代码")
@Data
public class DemoDto {

    @ApiModelProperty(value = "示例 - id", example = "1")
    private Long id;

    @ApiModelProperty(value = "示例 - name", example = "name")
    private String name;

    @ApiModelProperty(value = "示例 - num", example = "1")
    private Integer num;
}

