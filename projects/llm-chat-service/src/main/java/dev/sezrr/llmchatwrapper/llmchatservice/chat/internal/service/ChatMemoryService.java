package dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.service;

import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.contract.ChatMessageService;
import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.contract.ChatService;
import dev.sezrr.llmchatwrapper.llmchatservice.chat.exposed.dto.chatmessage.ChatMessageAddDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMemoryService implements ChatMemory {
    private final ChatService chatService;
    private final ChatMessageService chatMessageService;

    @Override
    public void add(String conversationId, List<Message> messages) {
        if (messages.isEmpty() || conversationId.isBlank()) {
            return;
        }

        var chat = chatService.isChatExists(UUID.fromString(conversationId));
        if (!chat)
            throw new IllegalArgumentException("Chat not found");

        List<ChatMessageAddDto> messagesToSave = messages.stream()
                .map(message -> new ChatMessageAddDto(
                        UUID.fromString(conversationId),
                        message.getText(),
                        message.getMessageType().getValue()))
                .toList();

        chatMessageService.batchSave(messagesToSave);
    }

    @Override
    public List<Message> get(String conversationId, int lastN) {
        var page = chatMessageService.getMessagesBefore(
                UUID.fromString(conversationId),
                null,
                lastN);

        return page.getContent().stream()
                .map(entity -> {
                    String text = entity.message();
                    MessageType role = entity.senderRole();
                    return switch (role) {
                        case USER      -> new UserMessage(text);
                        case ASSISTANT -> new AssistantMessage(text, Map.of());
                        case SYSTEM    -> new SystemMessage(text);
                        default       -> throw new IllegalStateException("Unknown role: " + role);
                    };
                })
                .collect(Collectors.toList());
    }

    @Override
    public void clear(String conversationId) {
        // No implementation needed for this example
    }
}
