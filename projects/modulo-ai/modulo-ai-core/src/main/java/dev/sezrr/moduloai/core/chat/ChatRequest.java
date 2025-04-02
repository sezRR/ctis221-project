package dev.sezrr.moduloai.core.chat;

public class ChatRequest {
    private String prompt;
    private String systemPrompt;
    private String userId;
    private String conversationId;
    private String messageId;

    public ChatRequest(String prompt, String systemPrompt, String userId, String conversationId, String messageId) {
        this.prompt = prompt;
        this.systemPrompt = systemPrompt;
        this.userId = userId;
        this.conversationId = conversationId;
        this.messageId = messageId;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public String getUserId() {
        return userId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getMessageId() {
        return messageId;
    }
    
    public static final class Builder {
        private String prompt;
        private String systemPrompt;
        private String userId;
        private String conversationId;
        private String messageId;

        public Builder setPrompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public Builder setSystemPrompt(String systemPrompt) {
            this.systemPrompt = systemPrompt;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
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

        public ChatRequest build() {
            return new ChatRequest(prompt, systemPrompt, userId, conversationId, messageId);
        }
    }
}
