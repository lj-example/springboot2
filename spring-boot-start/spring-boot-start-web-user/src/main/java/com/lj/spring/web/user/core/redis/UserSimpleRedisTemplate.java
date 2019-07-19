package com.lj.spring.web.user.core.redis;

import com.lj.spring.web.user.config.UserRedisProperties;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisAdvancedClusterCommands;
import io.lettuce.core.support.ConnectionPoolSupport;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by junli on 2019-07-01
 */
@Slf4j
public class UserSimpleRedisTemplate {

    private GenericObjectPool<StatefulRedisClusterConnection<String, String>> pool;

    public UserSimpleRedisTemplate(UserRedisProperties userRedisProperties) {
        this.pool = genericObjectPool(userRedisProperties);
    }

    /**
     * 执行指令
     */
    private <T> T execute(Function<RedisAdvancedClusterCommands, T> doInRedis) {
        try (StatefulRedisClusterConnection<String, String> connection = pool.borrowObject()) {
            RedisAdvancedClusterCommands<String, String> sync = connection.sync();
            return doInRedis.apply(sync);
        } catch (Exception e) {
            log.info("execute redis common error...", e);
        }
        return null;
    }

    /**
     * 获取一个 key 对应的 value
     */
    public String get(String key) {
        return execute(redisCommands -> (String) redisCommands.get(key));
    }

    /**
     * set 一个值
     */
    public String set(String key, String value) {
        return execute(redisCommands -> redisCommands.set(key, value));
    }

    /**
     * 获取一个 hash 对应的key
     */
    public String hGet(String hashKey, String fieldKey) {
        return execute(redisCommands -> (String) redisCommands.hget(hashKey, fieldKey));
    }

    /**
     * hset
     */
    public Boolean hSet(String hashKey, String fieldKey, String value) {
        return execute(redisCommands -> redisCommands.hset(hashKey, fieldKey, value));
    }

    /**
     * hash 获取所有
     */
    public Map<String, String> hGetAll(String hashKey) {
        return execute(redisCommands -> redisCommands.hgetall(hashKey));
    }

    /**
     * putAll
     */
    public void hPutAll(String hashKey, Map<String, String> map) {
        map.forEach((key, value) -> hSet(hashKey, key, value));
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, long expire) {
        return execute(redisCommands -> redisCommands.expire(key, expire));
    }

    /**
     * 设置过期时间
     */
    public Boolean expire(String key, int expire, TimeUnit timeUnit) {
        long l = timeUnit.toSeconds(expire);
        return execute(redisCommands -> redisCommands.expire(key, l));
    }

    /**
     * 删除一个key
     */
    public void del(String key) {
        execute(redisCommands -> redisCommands.del(key));
    }

    /**
     * 生成一个连接池
     * - 此处不生成 bean 避免污染全局 bean
     */
    private static GenericObjectPool genericObjectPool(UserRedisProperties userRedisProperties) {
        List<String> nodes = userRedisProperties.getCluster().getNodes();
        Assert.notEmpty(nodes, "user-redis nodes is empty");

        List<RedisURI> redisURIList = nodes.stream()
                .map(node -> {
                    String[] split = node.split(":");
                    RedisURI redisURI = RedisURI.Builder
                            .redis(split[0], Integer.valueOf(split[1]))
                            .build();
                    if (!StringUtils.isEmpty(userRedisProperties.getPassword())) {
                        redisURI.setPassword(userRedisProperties.getPassword());
                    }
                    return redisURI;
                })
                .collect(Collectors.toList());
        RedisClusterClient client = RedisClusterClient.create(redisURIList);
        //自定义连接属性
        client.setOptions(ClusterClientOptions.builder().build());
        //获取连接池配置信息
        GenericObjectPoolConfig<StatefulRedisClusterConnection<String, String>> poolConfig = new GenericObjectPoolConfig();
        UserRedisProperties.Pool lettucePool = userRedisProperties.getLettuce().getPool();
        if (Objects.isNull(lettucePool)) {
            lettucePool = new UserRedisProperties.Pool();
        }
        poolConfig.setMinIdle(lettucePool.getMinIdle());
        poolConfig.setMaxIdle(lettucePool.getMaxIdle());
        poolConfig.setMaxTotal(lettucePool.getMaxActive());
        poolConfig.setMaxWaitMillis(-1);
        return ConnectionPoolSupport.createGenericObjectPool(() -> client.connect(), poolConfig);
    }
}

