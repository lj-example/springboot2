package com.lj.demo.spring.config.web;

import com.lj.spring.web.tool.web.ExceptionAdvice;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * Created by junli on 2019-07-23
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 101)
public class ExceptionAdviceConfiguration extends ExceptionAdvice {

}

