package com.lj.spring.web.tool.restTemplate;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * Created by junli on 2019-06-28
 */
@Configuration
@AutoConfigureAfter({RestTemplateBuilder.class})
public class RestTemplateAutoConfiguration {

    @ConditionalOnMissingBean(RestTemplate.class)
    @Primary
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder templateBuilder) {
        return templateBuilder
                .setConnectTimeout(Duration.of(5, ChronoUnit.SECONDS))
                .setReadTimeout(Duration.of(5, ChronoUnit.SECONDS))
                .build();
    }
}

