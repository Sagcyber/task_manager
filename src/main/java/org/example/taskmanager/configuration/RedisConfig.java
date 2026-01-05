package org.example.taskmanager.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.time.Duration;

public class RedisConfig {
    
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        return RedisCacheManager.builder(factory)
                       .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig()
                                              .entryTtl(Duration.ofMinutes(10))
                       )
                       .build();
    }
}
