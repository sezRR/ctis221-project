package dev.sezrr.examples.llmchatservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.Modulithic;

@Modulithic
//@EnableJpaAuditing
@SpringBootApplication
public class LlmChatService {
    public static void main(String[] args) {        
        SpringApplication.run(LlmChatService.class, args);
    }
}
