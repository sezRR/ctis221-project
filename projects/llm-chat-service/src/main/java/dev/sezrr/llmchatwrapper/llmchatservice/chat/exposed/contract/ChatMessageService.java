package dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.contract;

import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.custom_response_entity.pagination.CursorPaginationResponse;

import java.util.List;
import java.util.UUID;

public interface ChatMessageService {
    void batchSave(List<ChatMessageAddDto> chatMessagesAddDto);
    
    CursorPaginationResponse<List<ChatMessageQueryDto>> getMessagesBefore(UUID chatId, UUID beforeMessageId, int size);
    ChatMessageQueryDto sendMessage(ChatMessageAddDto chatMessageAddDto);
    ChatMessageQueryDto updateMessage(UUID chatId, UUID messageId, String content);
}
