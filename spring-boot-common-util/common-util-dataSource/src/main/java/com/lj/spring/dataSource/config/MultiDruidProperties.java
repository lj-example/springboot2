package com.lj.spring.dataSource.config;

import com.lj.spring.dataSource.common.Prefix;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

/**
 * Created by lijun on 2019/4/29
 */
@Data
@ConfigurationProperties(prefix = Prefix.DATA_SOURCE_PROPERTIES)
public class MultiDruidProperties extends DruidDataSourceProperties {

    private MonitorProperties monitor = new MonitorProperties();
    private HashMap<String, DruidDataSourceProperties> dynamicDataSource;

    @Data
    public static class MonitorProperties {

        /**
         * 是否启用
         */
        private String enabled = "false";

        /**
         * 请求路径
         */
        private String druidStatView = "/druid/*";

        /**
         * 过滤规则
         */
        private String druidWebStatFilter = "/*";

        /**
         * 白名单
         */
        private String allow = "127.0.0.1";

        /**
         * 黑名单
         */
        private String deny;

        /**
         * 账号
         */
        private String loginUsername = "admin";

        /**
         * 密码
         */
        private String loginPassword = "admin";

        /**
         * 前端是否可以重置
         */
        private String resetEnable = "false";

        /**
         * 添加不需要忽略的格式信息.
         */
        private String exclusions = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";

        /**
         * 缺省sessionStatMaxCount
         */
        private String sessionStatMaxCount = "1000";

        /**
         * sessionStatEnable配置
         */
        private String sessionStatEnable = "false";
    }
}
