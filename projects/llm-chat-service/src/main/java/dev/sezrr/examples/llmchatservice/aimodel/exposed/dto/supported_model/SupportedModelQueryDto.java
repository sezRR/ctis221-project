package dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supported_model;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingQueryDto;

import java.io.Serializable;
import java.util.UUID;

public record SupportedModelQueryDto(
        UUID id, 
        String model, 
        String apiUrl, 
        ModelPricingQueryDto activeModelPricing
) implements Serializable {

}
