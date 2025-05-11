package dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.model.mapper;

import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chat.ChatAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chat.ChatQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.model.Chat;

public class ChatDtoMapper {
    private ChatDtoMapper() {
        // Private constructor to prevent instantiation
        throw new IllegalStateException("Mapper class cannot be instantiated");
    }
    
    public static ChatQueryDto mapToDto(Chat chat){
        return new ChatQueryDto(
                chat.getId(),
                chat.getUserId(),
                chat.getTitle(),
                chat.getDescription(),
                chat.getCreatedAt() != null ? chat.getCreatedAt().toString() : null,
                chat.getUpdatedAt() != null ? chat.getUpdatedAt().toString() : null
        );
    }
    
    public static Chat mapFromDto(ChatAddDto dto) {
        return new Chat(
                dto.userId(),
                dto.title(),
                dto.description()
        );
    }
}
