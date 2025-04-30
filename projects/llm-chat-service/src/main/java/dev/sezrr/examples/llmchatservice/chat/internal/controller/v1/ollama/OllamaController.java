package dev.sezrr.examples.llmchatservice.chat.internal.controller.v1.ollama;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ollama")
public class OllamaController {
    private final ChatClient chatClient;
    
    @PostMapping(value = "/{userId}/chats/{chatId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> home(@PathVariable String userId, @PathVariable String chatId, @RequestBody String message) {
        return chatClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .user(message)
                .stream()
                .chatResponse()
                .doOnNext(chatResponse -> {
                    System.out.println("Received chunk: " + chatResponse.getMetadata().getUsage().getTotalTokens());
                }).mapNotNull(chatResponse -> chatResponse.getResult().getOutput().getText())
                .doOnError(e -> {
                        if (e instanceof EntityNotFoundException) {
                            throw new EntityNotFoundException(e.getMessage());
                        } else {
                            throw new RuntimeException("An error occurred while processing the request", e);
                        }
                    }
                );
    }
}
