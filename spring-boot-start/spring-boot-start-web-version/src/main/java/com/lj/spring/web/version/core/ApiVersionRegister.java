package com.lj.spring.web.version.core;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Created by junli on 2019-09-05
 */
@RequiredArgsConstructor
public class ApiVersionRegister implements WebMvcRegistrations {

    /**
     * 使用新的 requestMapping 替代官方的
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new ApiVersionRequestMappingHandlerMapping();
    }
}
