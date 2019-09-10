package com.lj.demo.spring.config.swagger;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import com.lj.spring.common.head.HeadCommon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 参考地址信息 - https://doc.xiaominfo.com/guide/useful.html
 * 访问路径 http:{ip}:{port}/path/doc.html
 * Created by lijun on 2019/6/4
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config implements WebMvcConfigurer {

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
                .globalOperationParameters(HeadParameterEnum.getAllAsParameterList())
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
        return HeadParameterEnum.getAllAsParameterList();
    }

    /**************************- 配置公共头信息 开始-***************************/

    /**
     * 配置公共头信息
     */
    @AllArgsConstructor
    @Getter
    enum HeadParameterEnum {
        USER_TOKEN(HeadCommon.USER_TOKEN, "用户token,登录之后返回的值", "", "String", "header", false),
        CLIENT_VERSION(HeadCommon.VERSION, "客户端版本号", "1.0", "String", "header", false),
        LANG("LOCALE", "语言,eg:vi_VN,zh_CN,en_US", "zh_CN", "String", "header", false),
        PLATFORM(HeadCommon.PLATFORM, "来源 android ios h5", "", "String", "header", false),
        CHANNEL(HeadCommon.CHANNEL, "渠道 应用宝 小米商店", "", "String", "header", false),

        ;
        /**
         * 名称
         */
        private String name;

        /**
         * 描述
         */
        private String description;
        /**
         * 默认值
         */
        private String defaultValue;

        /**
         * 参数字段类型
         */
        private String modelRef;

        /**
         * 参数请求类型
         */
        private String parameterType;

        /**
         * 是否是必须字段
         */
        private Boolean required;

        /**
         * 获取参数集合
         */
        public static List<Parameter> getAllAsParameterList() {
            return Arrays.stream(HeadParameterEnum.values())
                    .map(headParameterEnum ->
                            new ParameterBuilder()
                                    .name(headParameterEnum.getName())
                                    .description(headParameterEnum.getDescription())
                                    .defaultValue(headParameterEnum.getDefaultValue())
                                    .modelRef(new ModelRef(headParameterEnum.getModelRef()))
                                    .parameterType(headParameterEnum.getParameterType())
                                    .required(headParameterEnum.getRequired())
                                    .build()
                    ).collect(Collectors.toList());
        }
    }
    /**************************- 配置公共头信息 结束-***************************/


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

    /**
     * 避免在404 异常抛出失败
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
