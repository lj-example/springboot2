package com.lj.demo.spring.config.apollo;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import com.lj.spring.common.apollo.namespace.Namespace;
import org.springframework.context.annotation.Configuration;

/**
 * Created by junli on 2019-09-25
 */
@Configuration
@EnableApolloConfig(value = {
        Namespace.APPLICATION,
        Namespace.STRING_DATA_SOURCE,
        Namespace.DYNAMIC_DATA_SOURCE,
        Namespace.STRING_REDIS,
        Namespace.STRING_MAIL
})
public class ApolloConfig {

}
