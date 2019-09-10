package com.lj.spring.web.version.support;


import com.lj.spring.web.version.core.ApiVersionRegister;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by junli on 2019-09-05
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(value = "spring.version.enable", havingValue = "true", matchIfMissing = true)
public class AutoApiVersionConfigure {

    @Bean
    public ApiVersionRegister apiVersionRegister() {
        return new ApiVersionRegister();
    }

}
