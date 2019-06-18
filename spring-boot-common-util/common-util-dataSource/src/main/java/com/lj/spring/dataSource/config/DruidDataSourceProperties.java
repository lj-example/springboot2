package com.lj.spring.dataSource.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

/**
 * Created by lijun on 2019/4/29
 */
@Data
@NoArgsConstructor
public class DruidDataSourceProperties {

    private String url;

    private String username;

    private String password;

    /**
     * 是否只读库
     */
    private boolean readonly;
    /**
     * 权重，方便负载均衡使用
     */
    private int weight;

    /**
     * 驱动
     */
    private String driverClassName;

    /** ---------------- **/
    /**
     * 连接池最大值
     */
    @Value("20")
    private Integer maxActive;

    /**
     * 初始大小
     */
    @Value("1")
    private Integer initialSize;

    /**
     * 连接等待最大时间
     */
    @Value("60000")
    private Integer maxWait;

    /**
     * 连接池最小值
     */
    @Value("1")
    private Integer minIdle;

    /** ---------------- **/
    /**
     * 有两个含义： 1)Destroy线程会检测连接的间隔时间2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    @Value("60000")
    private Integer timeBetweenEvictionRunsMillis;

    /**
     * 配置一个连接在池中最小生存的时间，单位是毫秒
     */
    @Value("300000")
    private Integer minEvictableIdleTimeMillis;


    @Value("select 'x'")
    private String validationQuery;

    /**
     * ----------------
     **/
    private boolean testWhileIdle = true;
    private boolean testOnBorrow = true;
    private boolean testOnReturn = false;

    /**
     * ----------------
     **/
    private boolean poolPreparedStatements = true;
    @Value("20")
    private Integer maxPoolPreparedStatementPerConnectionSize;


    private Integer psCacheSize;

    @Value("stat,slf4j")
    private String filters;

    private Properties connectionProperties;
}
