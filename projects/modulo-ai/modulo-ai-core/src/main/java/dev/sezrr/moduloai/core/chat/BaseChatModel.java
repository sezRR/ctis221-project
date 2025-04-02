package dev.sezrr.moduloai.core.chat;

import dev.sezrr.moduloai.core.provider.ModelProvider;

public abstract class BaseChatModel<TP extends ModelProvider> implements ChatModel {
    protected abstract ChatResponse mapResponseToChatResponse(String response);
}
