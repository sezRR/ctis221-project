package dev.sezrr.llmchatwrapper.frontendjavafxgui.core.request.model;

public record ModelPricingRequest(
        String modelId,
        Double additionalPrice,
        String details,
        Boolean active
) {
}
