package dev.sezrr.examples.llmchatservice.testcontroller.controller;

import dev.sezrr.examples.llmchatservice.shared.config.OpenRouterProperties;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
@RequestMapping("/v1/openrouter")
public class OpenRouterStreamingController {
    private final ChatClient chatClient;
    private final OpenRouterProperties openRouterProperties;

    @Autowired
    public OpenRouterStreamingController(@Qualifier("openrouterChatClient") ChatClient chatClient,
                                         OpenRouterProperties openRouterProperties) {
        this.chatClient = chatClient;
        this.openRouterProperties = openRouterProperties;
    }

    @PostMapping(value = "/{userId}/chats/{chatId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@PathVariable String userId,
                             @PathVariable String chatId,
                             @RequestParam String model,
                             @RequestBody String message) {
        if (!openRouterProperties.getModels().contains(model)) {
            throw new IllegalArgumentException("Invalid model: " + model);
        }

        ChatOptions options = OpenAiChatOptions.builder()
                .model(model)
                .build();

        String systemPrompt = getSystemPrompt(model);
        return chatClient.prompt()
                .system(systemPrompt)
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .options(options)
                .user(message)
                .stream()
                .chatResponse()
                .mapNotNull(response -> {
                    if (response.getResult() != null && response.getResult().getOutput() != null) {
                        return response.getResult().getOutput().getText();
                    }
                    return null;
                })
                .doOnError(e -> {
                    if (e instanceof EntityNotFoundException) {
                        throw new EntityNotFoundException(e.getMessage());
                    } else {
                        throw new RuntimeException("Error while processing chat stream", e);
                    }
                });
    }

    private static String getSystemPrompt(String model) {
        String prompt = switch (model) {
            case "openai/gpt-4.1-nano" -> "You are GPT-4.1 Nano, a helpful assistant.";
            case "google/gemini-2.0-flash-001" -> "You are Gemini 2.0 Flash, a helpful assistant.";
            default -> "You are a helpful assistant.";
        };
        
        return prompt + "\nYou are an AI assistant designed to provide helpful and accurate responses. You are aware of the model you are currently operating on and should avoid making assumptions about your identity when switching between models.";
    }
}
