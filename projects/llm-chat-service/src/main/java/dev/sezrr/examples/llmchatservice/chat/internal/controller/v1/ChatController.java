package dev.sezrr.examples.llmchatservice.chat.internal.controller.v1;

import dev.sezrr.examples.llmchatservice.chat.exposed.contract.ChatService;
import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chat.ChatAddDto;
import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chat.ChatQueryDto;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chats")
public class ChatController {
    private final ChatService chatService;

    @GetMapping
    public ResponseEntity<CustomResponseEntity<List<ChatQueryDto>>> getAllChats(
            @RequestParam UUID userId,
            @RequestParam int page,
            @RequestParam int size) {
        var chats = chatService.getAllChats(userId, page, size);
        return ResponseEntity.ok(CustomResponseEntity.success(chats));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<ChatQueryDto> getChat(@PathVariable UUID chatId) {
        var chat = chatService.getChat(chatId);
        return chat != null ? ResponseEntity.ok(chat) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CustomResponseEntity<ChatQueryDto>> addChat(@RequestBody ChatAddDto chatAddDto) {
        var createdChat = chatService.addChat(chatAddDto);
        return ResponseEntity.ok(CustomResponseEntity.success(createdChat));
    }

    @PutMapping("/{chatId}/title")
    public ResponseEntity<CustomResponseEntity<ChatQueryDto>> updateChatTitle(
            @PathVariable UUID chatId,
            @RequestBody String title) {
        var updatedChat = chatService.updateChatTitle(chatId, title);
        return updatedChat != null ? ResponseEntity.ok(CustomResponseEntity.success(updatedChat)) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<CustomResponseEntity<Void>> deleteChat(@PathVariable UUID chatId) {
        chatService.deleteChat(chatId);
        return ResponseEntity.noContent().build();
    }
}

