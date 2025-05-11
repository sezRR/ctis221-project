package dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.service;

import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.contract.OpenRouterService;
import dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.config.OpenRouterProperties;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@Service
public class OpenRouterServiceImpl implements OpenRouterService {
    private final ChatClient chatClient;
    private final OpenRouterProperties openRouterProperties;
    private final ChatMemory chatMemory;

    @Autowired
    public OpenRouterServiceImpl(@Qualifier("openrouterChatClient") ChatClient chatClient,
                                 OpenRouterProperties openRouterProperties, @Qualifier("chatMemoryService") ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.openRouterProperties = openRouterProperties;
        this.chatMemory = chatMemory;
    }
    
    @Override
    public Flux<String> sendMessage(String chatId, String userId, String model, String message) {
        if (!openRouterProperties.getModels().contains(model)) {
            throw new IllegalArgumentException("Invalid model: " + model);
        }

        ChatOptions options = OpenAiChatOptions.builder()
                .model(model)
                .build();

        UserMessage userMessage = UserMessage.builder()
                .text(message)
                .metadata(Map.of("model", model))
                .build();
        
        chatMemory.add(chatId, userMessage);

        String systemPrompt = getSystemPrompt(model);

        StringBuilder stringBuilder = new StringBuilder();
        return chatClient.prompt()
                .system(systemPrompt)
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .options(options)
                .messages(List.of(userMessage)) // 👈 inject message directly with metadata
                .stream()
                .chatResponse()
                .mapNotNull(response -> {
                    if (response.getResult() != null && response.getResult().getOutput() != null) {
                        return response.getResult().getOutput().getText();
                    }
                    return null;
                })
                .doOnNext(e -> {
                    stringBuilder.append(e);
                    System.out.println("Received message: " + e);
                })
                .doOnError(e -> {
                    if (e instanceof EntityNotFoundException) {
                        throw new EntityNotFoundException(e.getMessage());
                    } else {
                        throw new RuntimeException("Error while processing chat stream", e);
                    }
                })
                .doFinally(signal -> {
                    AssistantMessage aiMsg = new AssistantMessage(stringBuilder.toString(), Map.of("model", model));
                    chatMemory.add(chatId, List.of(aiMsg));
                });
    }

    @Cacheable(value = "openRouterModels")
    @Override
    public List<String> getSupportedModels() {
        return openRouterProperties.getModels();
    }

    private String getSystemPrompt(String model) {
        String prompt = switch (model) {
            case "openai/gpt-4.1-nano" -> "You are GPT-4.1 Nano, a helpful assistant.";
            case "google/gemini-2.0-flash-001" -> "You are Gemini 2.0 Flash, a helpful assistant.";
            default -> "You are a helpful assistant.";
        };

        return prompt + "\nYou are an AI assistant designed to provide helpful and accurate responses. You are aware of the model you are currently operating on and should avoid making assumptions about your identity when switching between models.";
    }
}
