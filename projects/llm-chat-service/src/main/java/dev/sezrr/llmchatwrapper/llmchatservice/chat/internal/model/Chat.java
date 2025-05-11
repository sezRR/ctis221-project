package dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.model;

import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.AuditEntity;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.uuid7.UuidV7;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "chats")
public class Chat extends AuditEntity {
    @Id
    @UuidV7 // TODO: Custom UUID Annotation
    private UUID id;
    private UUID userId;
    private String title;
    private String description;

    public Chat(UUID userId, String title, String description) {
        this.userId = userId;
        this.title = title;
        this.description = description;
    }

    // TODO: Implement a proper public chat system
    // private boolean isPublic;
}

