package com.lj.demo.bean;

import com.lj.demo.BaseWebTest;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;

/**
 * 验证 bean 注入与生命周期
 * Created by junli on 2019-08-29
 */
public class SelfBeanTest extends BaseWebTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void test() {
        //1. 获取 bean 描述器
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
        //2. 获取 添加参数
        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext.getParentBeanFactory();
        registry.registerBeanDefinition("name", beanDefinitionBuilder.getBeanDefinition());
    }

}
