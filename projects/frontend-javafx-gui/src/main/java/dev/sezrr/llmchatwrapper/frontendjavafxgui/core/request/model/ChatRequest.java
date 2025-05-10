package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model;

public record ChatRequest(
        String userId,
        String title,
        String description
) {
}