package dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.contract;

import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chat.ChatAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chat.ChatQueryDto;

import java.util.List;
import java.util.UUID;

public interface ChatService {
    List<ChatQueryDto> getAllChats(UUID userId, int page, int size);

    ChatQueryDto getChat(UUID chatId);

    ChatQueryDto addChat(ChatAddDto chatAddDto);
    
    ChatQueryDto updateChatTitle(UUID chatId, String title);

    void deleteChat(UUID chatId);
    
    boolean isChatExists(UUID chatId);
}
