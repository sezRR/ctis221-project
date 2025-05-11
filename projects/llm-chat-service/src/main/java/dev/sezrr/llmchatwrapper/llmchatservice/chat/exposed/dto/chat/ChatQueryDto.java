package dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chat;

import java.util.UUID;

public record ChatQueryDto(
        UUID chatId,
        UUID userId,
        String title,
        String description,
        String createdAt,
        String updatedAt
) {
}
