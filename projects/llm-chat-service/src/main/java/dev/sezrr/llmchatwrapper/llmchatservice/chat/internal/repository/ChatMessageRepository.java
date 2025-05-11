package dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.repository;

import dev.sezrr.llmchatwrapper.llmchatservice.chat.internal.model.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findByChatId(UUID chatId, Pageable pageable);
    Optional<ChatMessage> findByChatIdAndId(UUID chatId, UUID messageId);

    @Query("""
        SELECT m FROM ChatMessage m
        WHERE m.chatId = :chatId
          AND (:beforeId IS NULL OR m.createdAt < (
            SELECT cm.createdAt FROM ChatMessage cm WHERE cm.id = :beforeId
          ))
        ORDER BY m.createdAt DESC
    """)
    List<ChatMessage> findMessagesBefore(UUID chatId, UUID beforeId, Pageable pageable);
}
