package dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chat;

import java.util.UUID;

public record ChatAddDto(
        UUID userId,
        String title,
        String description
) {
}
