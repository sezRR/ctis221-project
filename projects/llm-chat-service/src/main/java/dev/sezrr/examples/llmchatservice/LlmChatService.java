package dev.sezrr.examples.llmchatservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.modulith.Modulithic;
import org.springframework.modulith.core.ApplicationModules;

@Modulithic
@EnableJpaAuditing
@SpringBootApplication
public class LlmChatService {
    public static void main(String[] args) {
//        ApplicationModules modules = ApplicationModules.of(LlmChatService.class);
//        modules.verify();
//        modules.forEach(System.out::println);
//        
        SpringApplication.run(LlmChatService.class, args);
    }
}
