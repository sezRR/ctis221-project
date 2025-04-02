package dev.sezrr.moduloai.core.chat;

public class ChatResponse {
    private String response;
    private String conversationId;
    private String messageId;

    public ChatResponse(String response, String conversationId, String messageId) {
        this.response = response;
        this.conversationId = conversationId;
        this.messageId = messageId;
    }

    public String getResponse() {
        return response;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getMessageId() {
        return messageId;
    }
    
    public static final class Builder {
        private String response;
        private String conversationId;
        private String messageId;

        public Builder setResponse(String response) {
            this.response = response;
            return this;
        }

        public Builder setConversationId(String conversationId) {
            this.conversationId = conversationId;
            return this;
        }

        public Builder setMessageId(String messageId) {
            this.messageId = messageId;
            return this;
        }

        public ChatResponse build() {
            return new ChatResponse(response, conversationId, messageId);
        }
    }
}
