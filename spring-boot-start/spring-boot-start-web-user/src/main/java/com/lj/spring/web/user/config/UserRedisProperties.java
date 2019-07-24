package com.lj.spring.web.user.config;

import com.lj.spring.web.user.common.Common;
import lombok.Data;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.List;

/**
 * {@link RedisProperties}
 * 单独配置 user-session 用以后续区分 业务 redis 服务 与 user-redis
 * Created by junli on 2019-07-01
 */
@ConfigurationProperties(prefix = "user-sessions.redis")
@Data
public class UserRedisProperties {

    /**
     * token 声明周期
     */
    private Long tokenExpireSecond = Common.TOKEN_EXPIRE_SECOND;

    /**
     * 被踢之后的 token 生存时间
     */
    private Long oldTokenExpireSecond = Common.OLD_TOKEN_EXPIRE_SECOND;

    /**
     * Database demo used by the connection factory.
     */
    private int database = 0;

    /**
     * Connection URL. Overrides host, port, and password. User is ignored. Example:
     * redis://user:password@example.com:6379
     */
    private String url;

    /**
     * Redis server host.
     */
    private String host = "localhost";

    /**
     * Login password of the redis server.
     */
    private String password;

    /**
     * Redis server port.
     */
    private int port = 6379;

    /**
     * Whether to enable SSL support.
     */
    private boolean ssl;

    /**
     * Connection timeout.
     */
    private Duration timeout;

    private UserRedisProperties.Cluster cluster;

    private final UserRedisProperties.Lettuce lettuce = new UserRedisProperties.Lettuce();


    /**
     * Pool properties.
     */
    @Data
    public static class Pool {

        /**
         * Maximum number of "idle" connections in the pool. Use a negative value to
         * indicate an unlimited number of idle connections.
         */
        private int maxIdle = 8;

        /**
         * Target for the minimum number of idle connections to maintain in the pool. This
         * setting only has an effect if it is positive.
         */
        private int minIdle = 0;

        /**
         * Maximum number of connections that can be allocated by the pool at a given
         * time. Use a negative value for no limit.
         */
        private int maxActive = 8;

        /**
         * Maximum amount of time a connection allocation should block before throwing an
         * exception when the pool is exhausted. Use a negative value to block
         * indefinitely.
         */
        private Duration maxWait = Duration.ofMillis(-1);

    }

    /**
     * Cluster properties.
     */
    @Data
    public static class Cluster {

        /**
         * Comma-separated list of "host:port" pairs to bootstrap from. This represents an
         * "initial" list of cluster nodes and is required to have at least one entry.
         */
        private List<String> nodes;

        /**
         * Maximum number of redirects to follow when executing commands across the
         * cluster.
         */
        private Integer maxRedirects;

    }

    /**
     * Lettuce client properties.
     */
    @Data
    public static class Lettuce {

        /**
         * Shutdown timeout.
         */
        private Duration shutdownTimeout = Duration.ofMillis(100);

        /**
         * Lettuce pool configuration.
         */
        private UserRedisProperties.Pool pool;

    }

}

