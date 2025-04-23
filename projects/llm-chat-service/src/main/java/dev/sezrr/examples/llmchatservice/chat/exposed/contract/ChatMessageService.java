package dev.sezrr.examples.llmchatservice.chat.exposed.contract;

import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageAddDto;
import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageQueryDto;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.pagination.CursorPaginationResponse;

import java.util.List;
import java.util.UUID;

public interface ChatMessageService {
    CursorPaginationResponse<List<ChatMessageQueryDto>> getMessagesBefore(UUID chatId, UUID beforeMessageId, int size);
    ChatMessageQueryDto sendMessage(ChatMessageAddDto chatMessageAddDto);
    ChatMessageQueryDto updateMessage(UUID chatId, UUID messageId, String content);
}
