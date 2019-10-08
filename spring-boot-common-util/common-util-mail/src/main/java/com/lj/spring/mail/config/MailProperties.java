package com.lj.spring.mail.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * 邮件配置
 * Created by lijun on 2019/4/9
 */
@Data
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {

    private MailFreeMarkConfigurer freeMark = new MailFreeMarkConfigurer();

    /**
     * 邮件主机地址
     */
    private String host = "smtp.exmail.qq.com";

    /**
     * 邮件主机端口
     */
    private Integer port = 465;

    /**
     * 用户名
     */
    private String username = "xxxx";

    /**
     * 昵称
     */
    private String nickname = "xxx";

    /**
     * 密码
     */
    private String password = "xxxx";

    /**
     * 是否开启调试
     */
    private Boolean debug = false;

    /**
     * 默认编码
     */
    private Charset charset = Charset.forName("UTF-8");

    /**
     * 发送邮件线程数
     */
    private Integer sendPoolSize = 5;

    /**
     * 其他配置
     */
    private HashMap<String, Object> properties;

    /**
     *
     */
    @Data
    public static class MailFreeMarkConfigurer {

        /**
         * 加载路径
         */
        private String templateLoaderPath = FreeMarkerProperties.DEFAULT_TEMPLATE_LOADER_PATH;

        /**
         * 编码
         */
        private String charset = Charset.forName("UTF-8").name();

        /**
         * 模板后缀
         */
        private String suffix = FreeMarkerProperties.DEFAULT_SUFFIX;

    }

    public MailProperties() {
        HashMap<String, Object> props = new HashMap<>();
        props.put("smtp.starttls.enable", true);
        props.put("smtp.starttls.required", true);
        props.put("mail.smtp.ssl.enable", true);
        props.put("mail.smtp.auth", true);
        props.put("mail.debug", this.debug);
        this.properties = props;
    }
}
