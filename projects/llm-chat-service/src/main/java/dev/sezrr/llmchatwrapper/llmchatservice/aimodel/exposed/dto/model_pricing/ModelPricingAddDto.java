package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.model_pricing;

public record ModelPricingAddDto(
        String modelId,
        Double additionalPrice,
        String details,
        Boolean active
) {
}
