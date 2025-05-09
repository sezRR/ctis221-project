package dev.sezrr.examples.llmchatservice.chat.exposed.dto.chatmessage;

import org.springframework.ai.chat.messages.MessageType;

import java.util.UUID;

public record ChatMessageQueryDto(
        UUID messageId,
        UUID chatId,
        String message,
        MessageType senderRole,
        String createdAt,
        String updatedAt
) {
}
