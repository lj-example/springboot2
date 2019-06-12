package com.lj.demo.spring.config.dataSource;

import com.lj.spring.dataSource.core.dynamic.DynamicAspectTemplate;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by lijun on 2019/5/9
 */
@Component("defaultDynamicAspectComponent")
@Aspect
public class DynamicAspectTemplateComponent extends DynamicAspectTemplate {

}
