package dev.sezrr.examples.llmchatservice.chat.internal.service;

import dev.sezrr.examples.llmchatservice.chat.exposed.contract.ChatService;
import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chat.ChatAddDto;
import dev.sezrr.examples.llmchatservice.chat.exposed.dto.chat.ChatQueryDto;
import dev.sezrr.examples.llmchatservice.chat.internal.model.Chat;
import dev.sezrr.examples.llmchatservice.chat.internal.model.mapper.ChatDtoMapper;
import dev.sezrr.examples.llmchatservice.chat.internal.repository.ChatRepository;
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
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;

    @Override
    public List<ChatQueryDto> getAllChats(UUID userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updatedAt"));
        return chatRepository.findByUserId(userId, pageable)
                .stream()
                .map(ChatDtoMapper::mapToDto)
                .toList();
    }

    @Override
    public ChatQueryDto getChat(UUID chatId) {
        if (!chatRepository.existsById(chatId))
            throw new EntityNotFoundException("Chat not found");
        
        return chatRepository.findById(chatId)
                .map(ChatDtoMapper::mapToDto)
                .orElse(null);
    }

    @Override
    public ChatQueryDto addChat(ChatAddDto chatAddDto) {
        Chat newChat = ChatDtoMapper.mapFromDto(chatAddDto);
        
        var addedChat = chatRepository.save(newChat);
        
        return ChatDtoMapper.mapToDto(addedChat);
    }

    @Override
    public ChatQueryDto updateChatTitle(UUID chatId, String title) {
        Chat chat = chatRepository.findById(chatId).orElse(null);
        if (chat == null)
            throw new EntityNotFoundException("Chat not found");
        
        chat.setTitle(title);
        var updatedChat = chatRepository.save(chat);
        
        return ChatDtoMapper.mapToDto(updatedChat);
    }

    @Override
    public void deleteChat(UUID chatId) {
        chatRepository.deleteById(chatId);
    }

    @Override
    public boolean isChatExists(UUID chatId) {
        return chatRepository.existsById(chatId);
    }
}
