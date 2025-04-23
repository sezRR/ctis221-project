package dev.sezrr.examples.llmchatservice.chat.internal.repository;

import dev.sezrr.examples.llmchatservice.chat.internal.model.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    List<Chat> findByUserId(UUID userId, Pageable pageable);
}
