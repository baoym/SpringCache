package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: com.example.demo.config
 * @Author: BYM
 * @Date: 2019/6/27
 * @Description:
 * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
 * 应的书面授权，承担保密责任和接受相应的法律约束.
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class CacheConfig extends CachingConfigurerSupport {

    /**
     * 自定义key.
     * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key
     */
//    @Bean
//    @Override
//    public KeyGenerator keyGenerator() {
//        return (o, method, objects) -> {
//            StringBuilder sb = new StringBuilder();
//            sb.append(o.getClass().getName());
//            sb.append(":");
//            sb.append(method.getName());
//            for (Object obj : objects) {
//                sb.append(":");
//                sb.append(obj.toString());
//            }
//            return sb.toString();
//        };
//    }


    //缓存管理器
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory factory) {
//        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
//        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//        // 设置缓存的默认过期时间，也是使用Duration设置
////        config = config.entryTtl(Duration.ofMinutes(10))
////                .disableCachingNullValues();     // 不缓存空值
//
//        // 设置一个初始化的缓存空间set集合
//        Set<String> cacheNames = new HashSet<>();
//        cacheNames.add("space");
//        cacheNames.add("user");
//
//        // 对每个缓存空间应用不同的配置
//        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
//        // 通过Duration可以自己实现以什么时间为单位
//        configMap.put("space", config.entryTtl(Duration.ofMinutes(1)));
//        configMap.put("user", config.entryTtl(Duration.ofSeconds(10)));
//
//        // 使用自定义的缓存配置初始化一个cacheManager
//        RedisCacheManager cacheManager = RedisCacheManager.builder(factory)
//                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
//                .withInitialCacheConfigurations(configMap)
//                .build();
//        return cacheManager;
//    }

    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);


        // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
                ;
        // 设置缓存的默认过期时间，也是使用Duration设置
//        config = config.entryTtl(Duration.ofMinutes(10))
//                .disableCachingNullValues();     // 不缓存空值

        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("space");
        cacheNames.add("user");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        // 通过Duration可以自己实现以什么时间为单位
        configMap.put("space", config.entryTtl(Duration.ofMinutes(1)));
        configMap.put("user", config.entryTtl(Duration.ofSeconds(10)));

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .cacheDefaults(config).build();
    }
//    @Bean
//    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        serializer.setObjectMapper(mapper);
//        template.setValueSerializer(serializer);
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofHours(1))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
//        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
//    }
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(factory);
//
//        /**
//         * 在这里，改用StringRedisSerializer序列化方式
//         *      a、setKeySerializer：对redis的key值 设置序列化方式
//         *      b、setValueSerializer：对redis的value值 设置序列化方式
//         *                            (若配置则只能接受String类型，其他类型需转为String类型)
//         */
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//
//        redisTemplate.setKeySerializer(stringRedisSerializer); // a
//        redisTemplate.setHashKeySerializer(stringRedisSerializer);
//        redisTemplate.setValueSerializer(stringRedisSerializer); // b
//        redisTemplate.setHashValueSerializer(stringRedisSerializer);
//
//        return redisTemplate;
//    }

//
//    @Bean
//    public RedisCacheManager redisCacheManager(RedisTemplate redisTemplate) {
//        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisTemplate.getConnectionFactory());
//        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
//                .entryTtl(Duration.ofHours(1))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));
//        return new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
//    }
}
