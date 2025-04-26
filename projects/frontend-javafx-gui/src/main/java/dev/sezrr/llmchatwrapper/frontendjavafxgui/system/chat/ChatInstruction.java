package dev.sezrr.llmchatwrapper.frontendjavafxgui.system.chat;

import java.util.UUID;

public class ChatInstruction {
    private final UUID id;
    private UUID userId;
    private UUID chatId;
    private String instruction;


    public ChatInstruction(UUID id) {
        this.id = id;
    }

    public ChatInstruction(UUID chatId, UUID id, UUID userId, String instruction) {
        this.chatId = chatId;
        this.id = id;
        this.userId = userId;
        this.instruction = instruction;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "\nChatInstruction{" +
                "\nChatId=" + chatId +
                "\nId=" + id +
                "\nUserId=" + userId +
                "\nInstruction='" + instruction + '\'' +
                '}';
    }
}
