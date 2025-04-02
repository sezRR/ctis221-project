package dev.sezrr.moduloai.core.chat;

import dev.sezrr.moduloai.core.model.Model;

public interface ChatModel extends Model {
    boolean supportsStreaming();
    
    // Requests
    ChatRequest createChatRequest(String prompt);
    ChatRequest createChatRequest(String prompt, String systemPrompt);
    
    // TODO: HISTORY
    
    
}
