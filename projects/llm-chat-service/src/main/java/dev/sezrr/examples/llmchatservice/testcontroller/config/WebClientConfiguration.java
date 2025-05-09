package dev.sezrr.examples.llmchatservice.testcontroller.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration  {
    @Bean
    public WebClient webclient() {
       return WebClient
                .builder()
                .baseUrl("http://localhost:3000")
                .build();
    }
}