package dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.modelPricing;

public record ModelPricingAddDto(
        String modelId,
        Double additionalPrice,
        String details,
        Boolean active
) {
}
