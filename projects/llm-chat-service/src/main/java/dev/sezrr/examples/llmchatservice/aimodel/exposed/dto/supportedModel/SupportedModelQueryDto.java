package dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.modelPricing.ModelPricingQueryDto;

import java.io.Serializable;
import java.util.UUID;

public record SupportedModelQueryDto(
        UUID id, 
        String model, 
        String apiUrl, 
        ModelPricingQueryDto activeModelPricing
) implements Serializable {

}
