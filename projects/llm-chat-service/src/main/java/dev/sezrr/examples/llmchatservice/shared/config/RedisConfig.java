package dev.sezrr.examples.llmchatservice.shared.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.Map;

@Configuration
@EnableCaching
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class RedisConfig {
    private final Map<String, RedisCacheConfiguration> cacheConfigurations;

    @Autowired
    public RedisConfig(Map<String, RedisCacheConfiguration> cacheConfigurations) {
        this.cacheConfigurations = cacheConfigurations;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues()
                .disableKeyPrefix()
                .serializeKeysWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                .entryTtl(Duration.ofMinutes(5)); // default TTL

        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultCacheConfig);

        if (cacheConfigurations != null)
            cacheConfigurations.forEach(builder::withCacheConfiguration);

        return builder.build();
    }
}