package com.bwie.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis 配置类
 * 作用：自定义 Redis 连接工厂与序列化规则
 * 解决问题：避免 Redisson 接管连接工厂导致的 zAdd 等命令循环调用 Bug
 * 采用方案：强制使用 Jedis 作为 Redis 客户端，而非 Lettuce/Redisson
 *
 * @author bwie
 * @date 2025-09-16
 */
@Configuration
public class RedisConfig {

    /**
     * 【核心】创建 Jedis 连接工厂
     * 作用：手动构建 Redis 连接工厂，强制使用 Jedis 客户端，覆盖自动配置
     * @Primary 表示优先使用当前 Bean，避免与其他连接工厂冲突
     *
     * @param redisProperties Spring 自动加载的 redis 配置（从 application.yml 读取）
     * @return Jedis 连接工厂
     */
    @Bean
    @Primary
    public RedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties) {
        // 1. 构建 Redis 单机连接配置（host/port/db/password）
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisProperties.getHost());          // Redis 服务地址
        config.setPort(redisProperties.getPort());              // Redis 端口
        config.setDatabase(redisProperties.getDatabase());      // Redis 数据库索引

        // 如果配置文件中设置了密码，则注入密码
        if (redisProperties.getPassword() != null) {
            config.setPassword(redisProperties.getPassword());
        }

        // 2. 构建 Jedis 连接池配置（最大连接、空闲连接等）
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        RedisProperties.Pool pool = redisProperties.getJedis().getPool(); // 读取 yml 中的 jedis 池配置
        poolConfig.setMaxTotal(pool.getMaxActive());    // 最大连接数
        poolConfig.setMaxIdle(pool.getMaxIdle());        // 最大空闲连接
        poolConfig.setMinIdle(pool.getMinIdle());        // 最小空闲连接

        // 3. 构建 Jedis 客户端配置（开启连接池）
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .usePooling()                // 启用连接池
                .poolConfig(poolConfig)      // 注入连接池配置
                .build();

        // 4. 返回 Jedis 连接工厂（最终交给 Spring 管理）
        return new JedisConnectionFactory(config, clientConfig);
    }

    /**
     * 自定义 RedisTemplate
     * 作用：统一 Redis 序列化规则，解决乱码、可读性差问题
     * 适用场景：存储 对象、List、Map、自增序列、分布式锁 等通用场景
     *
     * 序列化规则：
     *  - key/hashKey       → StringRedisSerializer（字符串明文，可读性强）
     *  - value/hashValue   → GenericJackson2JsonRedisSerializer（JSON 格式，自动转对象）
     *
     * @param jedisConnectionFactory 注入上面定义的 Jedis 连接工厂
     * @return 自定义好的 RedisTemplate<String, Object>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 绑定 Jedis 连接工厂
        template.setConnectionFactory(jedisConnectionFactory);

        // 字符串序列化器：用于 key/hashKey，保证 Redis 客户端明文可见
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // JSON 序列化器：用于 value/hashValue，自动把对象转 JSON，支持泛型
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

        // ==================== 核心序列化配置 ====================
        // 普通 Key 使用字符串序列化（业务key、序列key 必须用这个）
        template.setKeySerializer(stringSerializer);
        // Hash 类型的 Key 使用字符串序列化
        template.setHashKeySerializer(stringSerializer);

        // 普通 Value 使用 JSON 序列化（存储对象、数字、列表等）
        template.setValueSerializer(jsonSerializer);
        // Hash 类型的 Value 使用 JSON 序列化
        template.setHashValueSerializer(jsonSerializer);

        // 属性设置完成后，初始化模板
        template.afterPropertiesSet();

        return template;
    }

    /**
     * 注册 StringRedisTemplate（纯字符串场景专用）
     * 适用场景：
     *  - 自增序列 INCR / INCRBY
     *  - 计数器、分布式序号生成
     *  - 纯字符串读写（性能比 RedisTemplate 略高）
     *
     * 特点：key 和 value 默认都是 StringRedisSerializer
     *
     * @param jedisConnectionFactory Jedis 连接工厂
     * @return StringRedisTemplate
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory jedisConnectionFactory) {
        return new StringRedisTemplate(jedisConnectionFactory);
    }
}