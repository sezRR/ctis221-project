package dev.sezrr.llmchatwrapper.frontendjavafxgui;

import java.util.UUID;

public class ChatMessage {

    private final UUID id;
    private UUID chatId;
    private UserType senderType;
    private String message;


    public ChatMessage(UUID id) {
        this.id = id;
    }

    public ChatMessage(UUID id, UUID chatId, String message, UserType senderType) {
        this.id = id;
        this.chatId = chatId;
        this.message = message;
        senderType = senderType;
    }

    public UUID getId() {
        return id;
    }

    public UUID getChatId() {
        return chatId;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserType getUserType() {
        return senderType;
    }

    public void setUserType(UserType senderType) {
        senderType = senderType;
    }

    @Override
    public String toString() {
        return "\nChatMessage{" +
                "\nChatId=" + chatId +
                "\nId=" + id +
                "\nSenderType=" + senderType +
                "\nMessage='" + message + '\'' +
                '}';
    }
}

