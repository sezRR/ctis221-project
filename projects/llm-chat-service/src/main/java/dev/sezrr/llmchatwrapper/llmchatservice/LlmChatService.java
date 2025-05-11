package dev.sezrr.llmchatwrapper.llmchatservice;

import dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.config.OpenRouterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.modulith.Modulithic;

@EnableConfigurationProperties(OpenRouterProperties.class)
@ConfigurationPropertiesScan
@Modulithic
@SpringBootApplication
public class LlmChatService {
    public static void main(String[] args) {        
        SpringApplication.run(LlmChatService.class, args);
    }
}
