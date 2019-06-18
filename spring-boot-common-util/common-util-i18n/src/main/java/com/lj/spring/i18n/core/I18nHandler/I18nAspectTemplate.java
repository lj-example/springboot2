package com.lj.spring.i18n.core.I18nHandler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;

import java.util.Locale;
import java.util.Objects;

/**
 * Created by junli on 2019-06-14
 */
@Slf4j
public abstract class I18nAspectTemplate implements Ordered {

    /**
     * 设置初始化路径
     */
    public abstract void enablePath();

    /**
     * 获取语言信息
     */
    protected abstract Locale getLocaleInfo();

    @Before("enablePath()")
    public void before(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        I18nFolderName methodAnnotation = signature.getMethod().getAnnotation(I18nFolderName.class);
        if (Objects.nonNull(methodAnnotation)) {
            I18nResourceHandler.setInfo(methodAnnotation.name());
        } else {
            //设置文件夹路径
            Object pointThis = joinPoint.getThis();
            I18nFolderName annotation = pointThis.getClass().getAnnotation(I18nFolderName.class);
            if (Objects.nonNull(annotation)) {
                I18nResourceHandler.setInfo(annotation.value());
            }
        }
        //设置语言信息
        Locale localeInfo = getLocaleInfo();
        if (Objects.nonNull(localeInfo)) {
            I18nResourceHandler.setInfo(getLocaleInfo());
        }
    }

    @AfterReturning("enablePath()")
    public void after() {
        I18nResourceHandler.clean();
    }

    @AfterThrowing("enablePath()")
    public void exception() {
        I18nResourceHandler.clean();
    }

    /**
     * 在最上级获取多语言信息
     */
    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}

