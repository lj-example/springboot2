/**
 * 定义所有的 swagger 文档信息
 *
 * Swagger 常用注解参考 https://github.com/swagger-api/swagger-core/wiki/Annotations
 *
 * @ApiIgnore 用于类或者方法上，可以不被swagger显示在页面上
 *
 * @ApiImplicitParam 参数说明：
 *   - paramType：指定参数放在哪个地方
 *      - header：请求参数放置于Request Header，使用@RequestHeader获取
 *      - query：请求参数放置于请求地址，使用@RequestParam获取
 *      - path：（用于restful接口）-->请求参数的获取：@PathVariable
 *   - name：参数名
 *   - value：说明参数的意思
 *   - required：参数是否必须传
 *   - defaultValue：参数的默认值
 *   - dataTypeClass：参数类型
 *   ... 其他
 *
 * @ApiResponse 参数说明：
 *   - code：状态码
 *   - message: 描述信息
 *   - response: 抛出的异常信息
 *   ... 其他
 *
 * @ApiModelProperty 参数说明：
 *   - value: 字段说明
 *   - name: 重写属性字段名称
 *   - dataType: 类型
 *   - required: 是否必填
 *   - example: 示例值
 *   - hidden: 是否隐藏
 *   ... 其他
 *
 * Created by lijun on 2019/6/4
 */
package com.lj.demo.api;