package dev.sezrr.examples.llmchatservice.chat.exposed.dto.chatmessage;

import java.util.UUID;

public record ChatMessageAddDto(
        UUID chatId,
        String message,
        String role
) {
}
