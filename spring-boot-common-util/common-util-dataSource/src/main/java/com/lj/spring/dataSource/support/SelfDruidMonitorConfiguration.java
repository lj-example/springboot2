package com.lj.spring.dataSource.support;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.lj.spring.dataSource.common.Prefix;
import com.lj.spring.dataSource.config.MultiDruidProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * Created by lijun on 2019/4/29
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(name = SelfDruidMonitorConfiguration.NAME, havingValue = SelfDruidMonitorConfiguration.NAME_DEFAULT_VALUE)
@EnableConfigurationProperties(MultiDruidProperties.class)
public class SelfDruidMonitorConfiguration {

    /**
     * 默认开启该配置对应的名称
     */
    static final String NAME = Prefix.DATA_SOURCE_MONITOR_PROPERTIES + ".enable";

    /**
     * 默认开启该配置对应的名称 - 值
     */
    static final String NAME_DEFAULT_VALUE = "enable";

    private final MultiDruidProperties multiDruidProperties;

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidServlet() {
        MultiDruidProperties.MonitorProperties monitor = multiDruidProperties.getMonitor();
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>();
        servletRegistrationBean.setServlet(new StatViewServlet());
        final ArrayList<String> list = new ArrayList<>();
        list.add(monitor.getDruidStatView());
        servletRegistrationBean.setUrlMappings(list);
        // IP白名单
        if (!StringUtils.isEmpty(monitor.getAllow())) {
            servletRegistrationBean.addInitParameter("allow", monitor.getAllow());
        }
        // IP黑名单(共同存在时，deny优先于allow)
        if (!StringUtils.isEmpty(monitor.getDeny())) {
            servletRegistrationBean.addInitParameter("deny", monitor.getDeny());
        }
        //控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", monitor.getLoginUsername());
        servletRegistrationBean.addInitParameter("loginPassword", monitor.getLoginPassword());
        //是否能够重置数据 禁用HTML页面上的“Reset All”功能
        servletRegistrationBean.addInitParameter("resetEnable", monitor.getResetEnable());
        log.info(">>>>> 初始化【druid监控】 完成");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
        MultiDruidProperties.MonitorProperties monitor = multiDruidProperties.getMonitor();
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns(monitor.getDruidWebStatFilter());
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", monitor.getExclusions());
        //初始缺省sessionStatMaxCount是1000个
        filterRegistrationBean.addInitParameter("sessionStatMaxCount",monitor.getSessionStatMaxCount());
        //关闭session 统计功能
        filterRegistrationBean.addInitParameter("sessionStatEnable",monitor.getSessionStatEnable());
        return filterRegistrationBean;
    }
}