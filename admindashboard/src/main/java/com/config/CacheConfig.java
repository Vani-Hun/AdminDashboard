package com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.clients.jedis.Jedis;

@Configuration
@EnableRedisRepositories
public class CacheConfig {

    @Value("${redis.url}")
    private String redisUrl;

    @Bean
    public Jedis jedis() {
        return new Jedis(redisUrl);
    }
}


