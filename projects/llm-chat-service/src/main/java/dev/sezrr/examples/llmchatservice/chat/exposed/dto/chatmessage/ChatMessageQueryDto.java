package dev.sezrr.examples.llmchatservice.chat.exposed.dto.chatmessage;

import dev.sezrr.examples.llmchatservice.chat.internal.model.SenderRole;

import java.util.UUID;

public record ChatMessageQueryDto(
        UUID messageId,
        UUID chatId,
        String message,
        SenderRole senderRole,
        String createdAt,
        String updatedAt
) {
}
