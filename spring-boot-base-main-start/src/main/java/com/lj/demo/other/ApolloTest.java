package com.lj.demo.other;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.lj.spring.common.apollo.namespace.Namespace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用以验证 Apollo 各项功能
 * Created by junli on 2019-09-26
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ApolloTest {

    /**
     * 测试监听器
     */
    @ApolloConfigChangeListener(value = Namespace.APPLICATION)
    public void listener(ConfigChangeEvent configChangeEvent) {
        configChangeEvent.changedKeys().forEach(key -> {
            ConfigChange configChange = configChangeEvent.getChange(key);
            System.out.println(configChange.getNamespace()
                    + ":" + configChange.getChangeType()
                    + ":" + configChange.getPropertyName()
                    + ":" + configChange.getNewValue()
                    + ":" + configChange.getOldValue());
        });
    }


}
