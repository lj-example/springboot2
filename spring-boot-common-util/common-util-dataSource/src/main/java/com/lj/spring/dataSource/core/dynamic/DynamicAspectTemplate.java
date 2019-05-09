package com.lj.spring.dataSource.core.dynamic;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by lijun on 2019/5/8
 */
@Slf4j
public class DynamicAspectTemplate implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    /**
     * 声明数据库类型
     */
    @Pointcut("@annotation(com.lj.spring.dataSource.core.dynamic.DataSourceType)")
    protected void dynamicMethod() {
    }

    @Before("dynamicMethod()")
    public void before(JoinPoint joinPoint) {
        //数据库类型
        String dataType;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        final Method method = signature.getMethod();
        DataSourceType dataSourceType = method.getAnnotation(DataSourceType.class);
        if (Objects.nonNull(dataSourceType)) {
            dataType = dataSourceType.value();
        } else {
            dataType = DynamicCommon.MASTER_NAME;
        }
        DynamicHandler.set(dataType);
    }

    @AfterReturning("dynamicMethod()")
    public void after() {
        DynamicHandler.clean();
    }

    @AfterThrowing("dynamicMethod()")
    public void exception() {
        DynamicHandler.clean();
    }
}
