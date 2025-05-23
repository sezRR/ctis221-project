package dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.model.mapper;

import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.model.ChatMessage;

public class ChatMessageDtoMapper {
    private ChatMessageDtoMapper() {
        // Private constructor to prevent instantiation
        throw new IllegalStateException("Mapper class cannot be instantiated");
    }
    
    public static ChatMessageQueryDto mapToDto(ChatMessage chatMessage) {
        return new ChatMessageQueryDto(
                chatMessage.getId(),
                chatMessage.getChatId(),
                chatMessage.getMessage(),
                chatMessage.getSenderRole(),
                chatMessage.getModel(),
                chatMessage.getCreatedAt() != null ? chatMessage.getCreatedAt().toString() : null,
                chatMessage.getUpdatedAt() != null ? chatMessage.getUpdatedAt().toString() : null
        );
    }
    
    public static ChatMessage mapFromDto(ChatMessageAddDto dto) {
        return new ChatMessage(
                dto.chatId(),
                dto.message(),
                dto.role(),
                dto.model()
        );
    }

    public static ChatMessage mapFromDto(ChatMessageQueryDto dto) {
        return new ChatMessage(
                dto.chatId(),
                dto.message(),
                dto.senderRole()
        );
    }
}
