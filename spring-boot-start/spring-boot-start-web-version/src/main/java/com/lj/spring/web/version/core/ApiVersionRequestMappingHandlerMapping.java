package com.lj.spring.web.version.core;


import com.lj.spring.web.version.annotation.ApiClientInfo;
import com.lj.spring.web.version.annotation.ApiVersion;
import com.lj.spring.web.version.common.VersionUtil;
import com.lj.spring.web.version.core.expression.ApiVersionExpression;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by junli on 2019-09-05
 */
public class ApiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    /**
     * 通过当前的类 获取 RequestCondition
     *
     * @param handlerType 当前类
     * @return RequestCondition
     */
    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(handlerType, ApiVersion.class);
        if (Objects.isNull(apiVersion)) {
            return null;
        }
        ApiVersionExpression expression = ApiVersionExpression.of(apiVersion);
        return ApiVersionCondition.of(expression);
    }

    /**
     * 通过当前方法获取 RequestCondition
     *
     * @param method 当前方法
     * @return RequestCondition
     */
    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        ApiClientInfo apiClientInfo = AnnotatedElementUtils.findMergedAnnotation(method, ApiClientInfo.class);
        if (Objects.isNull(apiClientInfo)) {
            return null;
        }
        ApiVersionExpression expression = ApiVersionExpression.of(apiClientInfo);
        return ApiVersionCondition.of(expression);
    }

    /**
     * 根据类和方法获取 RequestMappingInfo
     *
     * @param method      方法
     * @param handlerType 当前类
     * @return RequestMappingInfo
     */
    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo mappingForMethod = super.getMappingForMethod(method, handlerType);
        //尝试从 当前类上获取 配置的路径信息，用以更改路径
        if (Objects.isNull(mappingForMethod)) {
            return null;
        }
        ApiVersion apiVersionAnnotation = AnnotatedElementUtils.findMergedAnnotation(handlerType, ApiVersion.class);
        if (Objects.isNull(apiVersionAnnotation)) {
            return mappingForMethod;
        }
        final String version = apiVersionAnnotation.value();
        if (StringUtils.isBlank(version)) {
            return mappingForMethod;
        }
        //合并方法 - 生成新的匹配路径
        return RequestMappingInfo.paths(VersionUtil.formatVersionStr(version)).build()
                .combine(mappingForMethod);
    }

    /**
     * 该方法在getMappingForMethod 中会调用，留作以后扩展使用
     *
     * @param requestMapping  方法上的 RequestMapping 注解
     * @param customCondition 自定义的Condition,当前只想 ApiVersionCondition
     * @return RequestMappingInfo
     */
    @Override
    protected RequestMappingInfo createRequestMappingInfo(RequestMapping requestMapping, RequestCondition<?> customCondition) {
        return super.createRequestMappingInfo(requestMapping, customCondition);
    }

    /**
     * 此处定义了请求方法的跨域请求处理
     *
     * @param handler     请求类
     * @param method      请求方法
     * @param mappingInfo 记录信息
     * @return 跨越配置
     */
    @Override
    protected CorsConfiguration initCorsConfiguration(Object handler, Method method, RequestMappingInfo mappingInfo) {
        return super.initCorsConfiguration(handler, method, mappingInfo);
    }
}
