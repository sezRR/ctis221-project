package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat;

import java.util.UUID;

public class ChatDetail {
    private final UUID id;
    private UUID userId;
    private UUID modelId;
    private String title;

    public ChatDetail(UUID id) {
        this.id = id;
    }

    public ChatDetail(UUID id, UUID modelId, String title, UUID userId) {
        this.id = id;
        this.modelId = modelId;
        this.title = title;
        this.userId = userId;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getModelId() {
        return modelId;
    }

    public void setModelId(UUID modelId) {
        this.modelId = modelId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "\nChatDetail{" +
                "\nId=" + id +
                "\nUserId=" + userId +
                "\nModelId=" + modelId +
                "\nTitle='" + title + '\'' +
                '}';
    }
}
