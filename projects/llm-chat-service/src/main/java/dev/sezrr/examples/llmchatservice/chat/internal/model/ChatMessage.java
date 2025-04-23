package dev.sezrr.examples.llmchatservice.chat.internal.model;

import dev.sezrr.examples.llmchatservice.shared.model.uuid7.UuidV7;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chat_messages")
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {
    @Id
    @UuidV7
    private UUID id;
    private UUID chatId;
    private String message;
    
    @Enumerated(EnumType.STRING)
    private SenderRole senderRole;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ChatMessage(UUID chatId, String message, String role) {
        this.chatId = chatId;
        this.message = message;
        this.senderRole = SenderRole.valueOf(role);
    }
}
