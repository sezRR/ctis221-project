package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model.mapper;

import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model.ModelPricing;

public class ModelPricingDtoMapper {
    private ModelPricingDtoMapper() {
        // Private constructor to prevent instantiation
        throw new IllegalStateException("Mapper class cannot be instantiated");
    }
    
    public static ModelPricingQueryDto mapToDto(ModelPricing entity) {
        return new ModelPricingQueryDto(
                entity.getId(),
                entity.getAdditionalPrice(),
                entity.getDetails(),
                entity.isActive()
        );
    }
}
