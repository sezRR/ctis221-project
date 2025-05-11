package dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.contract;

import reactor.core.publisher.Flux;

import java.util.List;

public interface OpenRouterService {
    Flux<String> sendMessage(String chatId, String userId, String model, String message);
    
    List<String> getSupportedModels();
}
