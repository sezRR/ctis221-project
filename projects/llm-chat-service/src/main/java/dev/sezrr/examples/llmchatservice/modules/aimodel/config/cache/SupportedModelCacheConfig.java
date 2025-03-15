package dev.sezrr.examples.llmchatservice.modules.aimodel.config.cache;

import dev.sezrr.examples.llmchatservice.modules.aimodel.services.constants.SupportedModelConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

import java.time.Duration;

@Configuration
public class SupportedModelCacheConfig {
    @Bean(name = SupportedModelConstants.SUPPORTED_MODEL_CACHE_NAME)
    public RedisCacheConfiguration supportedModelCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10));
    }
}
