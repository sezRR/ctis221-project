package dev.sezrr.examples.llmchatservice.chat.internal.service;

import dev.sezrr.examples.llmchatservice.chat.exposed.contract.ChatMessageService;
import dev.sezrr.examples.llmchatservice.chat.exposed.contract.ChatService;
import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageAddDto;
import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageQueryDto;
import dev.sezrr.examples.llmchatservice.chat.internal.model.ChatMessage;
import dev.sezrr.examples.llmchatservice.chat.internal.model.mapper.ChatMessageDtoMapper;
import dev.sezrr.examples.llmchatservice.chat.internal.repository.ChatMessageRepository;
import dev.sezrr.examples.llmchatservice.shared.custom_response_entity.pagination.CursorPaginationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatService chatService;


    @Override
    public CursorPaginationResponse<List<ChatMessageQueryDto>> getMessagesBefore(UUID chatId, UUID beforeMessageId, int size) {
        if (!chatService.isChatExists(chatId))
            throw new EntityNotFoundException("Chat not found");

        ChatMessage beforeMessage = null;
        if (beforeMessageId != null) {
            beforeMessage = chatMessageRepository.findById(beforeMessageId)
                    .orElseThrow(() -> new EntityNotFoundException("Message not found"));
        }

        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<ChatMessage> messages = beforeMessage == null
                ? chatMessageRepository.findByChatId(chatId, pageable)
                : chatMessageRepository.findMessagesBefore(chatId, beforeMessageId, pageable);

        List<ChatMessageQueryDto> chatMessageResults = messages.stream()
                .map(ChatMessageDtoMapper::mapToDto)
                .toList();

        boolean hasMore = messages.size() == size;
        UUID nextCursor = hasMore ? messages.get(messages.size() - 1).getId() : null;

        return new CursorPaginationResponse<>(chatMessageResults, hasMore, nextCursor);
    }

    @Override
    public ChatMessageQueryDto sendMessage(ChatMessageAddDto chatMessageAddDto) {
        if (!chatService.isChatExists(chatMessageAddDto.chatId()))
            throw new EntityNotFoundException("Chat not found");
        
        ChatMessage newMessage = ChatMessageDtoMapper.mapFromDto(chatMessageAddDto);
        newMessage = chatMessageRepository.save(newMessage);

        return ChatMessageDtoMapper.mapToDto(newMessage);
    }

    @Override
    public ChatMessageQueryDto updateMessage(UUID chatId, UUID messageId, String content) {
        ChatMessage message = chatMessageRepository.findByChatIdAndId(chatId, messageId).orElse(null);
        if (message == null)
            throw new EntityNotFoundException("Chat message not found");

        message.setMessage(content);
        var updatedMessage = chatMessageRepository.save(message);

        return ChatMessageDtoMapper.mapToDto(updatedMessage);
    }
}
