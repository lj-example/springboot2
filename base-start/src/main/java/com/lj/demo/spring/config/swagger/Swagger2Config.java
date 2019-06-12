package com.lj.demo.spring.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 参考地址信息 - https://doc.xiaominfo.com/guide/useful.html
 * 访问路径 http:{ip}:{port}/path/doc.html
 * Created by lijun on 2019/6/4
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {

    /**
     * 标题信息
     */
    final static String TITLE = "标题信息";

    /**
     * 描述信息
     */
    final static String DESCRIPTION = "描述信息";

    /**
     * 服务条款网址
     */
    final static String VERSION = "2.0";

    /**
     * 扫描包路径
     */
    final static String BASE_PACKAGE = "com.lj.demo.controller";

    /**
     * 定义全局的Code 信息
     */
    final static List<ResponseMessage> responseMessageList = new ArrayList<>();

    static {
        responseMessageList.add(buildResponseMessageByHttStatus(HttpStatus.NOT_FOUND));
        responseMessageList.add(buildResponseMessageByHttStatus(HttpStatus.BAD_REQUEST));
        responseMessageList.add(buildResponseMessageByHttStatus(HttpStatus.UNAUTHORIZED));
        responseMessageList.add(buildResponseMessageByHttStatus(HttpStatus.INTERNAL_SERVER_ERROR));
        responseMessageList.add(buildResponseMessageByHttStatus(HttpStatus.FORBIDDEN));
        //    responseMessageList.add(buildResponseMessageByHttStatus(HttpStatus.OK));
    }

    @Bean(value = "defaultApi")
    public Docket controllerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //是否启用
                .enable(true)
                .globalResponseMessage(RequestMethod.GET, responseMessageList)
                .globalResponseMessage(RequestMethod.POST, responseMessageList)
                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
                .globalResponseMessage(RequestMethod.OPTIONS, responseMessageList)
                .useDefaultResponseMessages(false)
                .groupName("分组信息")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
                .paths(PathSelectors.any())
                .build();
        // .globalOperationParameters(parameterList());
    }

    /**
     * 构建一个ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION)
                .build();
    }

    /**
     * 返回全局参数设置列表
     */
    private List<Parameter> parameterList() {
        ArrayList<Parameter> parameterArrayList = Lists.newArrayList();
        Parameter tokenParameter = new ParameterBuilder()
                .name("headToken")
                .description("headToken")
                .defaultValue("tokenInfo")
                .modelRef(new ModelRef("String"))
                .parameterType("header")
                .required(false)
                .build();
        parameterArrayList.add(tokenParameter);
        return parameterArrayList;
    }

    /**
     * 根据Http状态码构建描述信息
     */
    private static ResponseMessage buildResponseMessageByHttStatus(HttpStatus httpStatus) {
        return new ResponseMessageBuilder()
                .code(httpStatus.value())
                .message("状态信息：" + httpStatus.getReasonPhrase())
                .responseModel(new ModelRef("ApiError"))
                .build();
    }
}
