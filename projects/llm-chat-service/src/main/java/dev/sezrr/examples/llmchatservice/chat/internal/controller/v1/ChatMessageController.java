package dev.sezrr.examples.llmchatservice.chat.internal.controller.v1;

import dev.sezrr.examples.llmchatservice.chat.exposed.contract.ChatMessageService;
import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageAddDto;
import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageQueryDto;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.CustomResponseEntity;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.pagination.CursorPaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/chats/{chatId}/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    @GetMapping
    public ResponseEntity<CustomResponseEntity<CursorPaginationResponse<List<ChatMessageQueryDto>>>> getAllMessages(
            @PathVariable UUID chatId,
            @RequestParam(required = false) UUID beforeMessageId,
            @RequestParam int size) {

        var messages = chatMessageService.getMessagesBefore(chatId, beforeMessageId, size);
        return ResponseEntity.ok(CustomResponseEntity.success(messages));
    }

    @PostMapping
    public ResponseEntity<CustomResponseEntity<ChatMessageQueryDto>> sendMessage(
            @RequestBody ChatMessageAddDto chatMessageAddDto) {

        var createdMessage = chatMessageService.sendMessage(chatMessageAddDto);
        return ResponseEntity.ok(CustomResponseEntity.success(createdMessage));
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<CustomResponseEntity<ChatMessageQueryDto>> updateMessage(
            @PathVariable UUID chatId,
            @PathVariable UUID messageId,
            @RequestParam String content) {

        var updatedMessage = chatMessageService.updateMessage(chatId, messageId, content);
        return Optional.ofNullable(updatedMessage)
                .map(message -> ResponseEntity.ok(CustomResponseEntity.success(message)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
