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

@Component("chatMemoryService")
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
        
        List<Message> filteredMessages = messages.stream()
                .filter(message -> message.getMessageType() != MessageType.SYSTEM)
                .filter(message -> message.getMetadata().containsKey("model"))
                .toList();
        
        List<ChatMessageAddDto> messagesToSave = filteredMessages.stream()
                .map(message -> new ChatMessageAddDto(
                        UUID.fromString(conversationId),
                        message.getText(),
                        message.getMessageType().getValue(),
                        message.getMetadata().get("model").toString()))
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
                    String model = entity.model();
                    
                    return switch (role) {
                        case USER -> UserMessage.builder()
                                .text(text)
                                .metadata(Map.of("model", model))
                                .build();
                        case ASSISTANT -> new AssistantMessage(text, Map.of("model", model));
                        case SYSTEM -> new SystemMessage(text);
                        default -> throw new IllegalStateException("Unknown role: " + role);
                    };
                }).collect(Collectors.toList());
    }

    @Override
    public void clear(String conversationId) {
        // No implementation needed for this example
    }
}
