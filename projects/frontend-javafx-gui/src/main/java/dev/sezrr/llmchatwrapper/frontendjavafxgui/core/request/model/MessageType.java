package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model;

public enum MessageType {
    USER("user"),
    ASSISTANT("assistant"),
    SYSTEM("system"),
    TOOL("tool");

    private final String value;

    private MessageType(String value) {
        this.value = value;
    }

    public static MessageType fromValue(String value) {
        for(MessageType messageType : values()) {
            if (messageType.getValue().equals(value)) {
                return messageType;
            }
        }

        throw new IllegalArgumentException("Invalid MessageType value: " + value);
    }

    public String getValue() {
        return this.value;
    }
}
