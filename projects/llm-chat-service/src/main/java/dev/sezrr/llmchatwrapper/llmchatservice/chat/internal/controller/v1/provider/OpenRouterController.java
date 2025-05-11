package dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.controller.v1.provider;

import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.contract.OpenRouterService;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/providers/openrouter")
public class OpenRouterController {
    private final OpenRouterService openRouterService;
    
    @GetMapping("/models")
    public ResponseEntity<CustomResponseEntity<List<String>>> getSupportedModels() {
        return ResponseEntity.ok(CustomResponseEntity.success(openRouterService.getSupportedModels()));
    }

    @PostMapping(value = "/{userId}/chats/{chatId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@PathVariable String userId,
                             @PathVariable String chatId,
                             @RequestParam String model,
                             @RequestBody String message) {
        return openRouterService.sendMessage(userId, chatId, model, message);
    }
}
