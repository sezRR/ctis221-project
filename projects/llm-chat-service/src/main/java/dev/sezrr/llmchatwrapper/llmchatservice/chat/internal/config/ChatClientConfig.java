package dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    @Qualifier("ollamaChatClient")
    public ChatClient chatClient(
            @Qualifier("ollamaChatModel") ChatModel ollamaChatModel,
            @Qualifier("chatMemoryService") ChatMemory chatMemory
    ) {
        return ChatClient.builder(ollamaChatModel)
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))
                .build();
    }
}
