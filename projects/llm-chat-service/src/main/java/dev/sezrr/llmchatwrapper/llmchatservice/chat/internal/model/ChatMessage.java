package dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.model;

import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.uuid7.UuidV7;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_messages")
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage implements Message {
    @Id
    @UuidV7
    private UUID id;
    private UUID chatId;
    
    @Column(columnDefinition = "TEXT")
    private String message;
    
    @Enumerated(EnumType.STRING)
    private MessageType senderRole;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ChatMessage(UUID chatId, String message, String role) {
        this.chatId = chatId;
        this.message = message;
        this.senderRole = MessageType.fromValue(role);
    }

    public ChatMessage(UUID chatId, String message, MessageType role) {
        this.chatId = chatId;
        this.message = message;
        this.senderRole = role;
    }


    @Override
    public MessageType getMessageType() {
        return this.senderRole;
    }

    @Override
    public String getText() {
        return this.message;
    }

    @Override
    public Map<String, Object> getMetadata() {
        return Map.of();
    }
}
