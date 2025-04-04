package dev.sezrr.examples.llmchatservice.aimodel.internal.model.mapper;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.modelPricing.ModelPricingQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.ModelPricing;

public class ModelPricingDtoMapper {
    public static ModelPricingQueryDto mapToDto(ModelPricing entity) {
        return new ModelPricingQueryDto(
                entity.getId(),
                entity.getAdditionalPrice(),
                entity.getDetails(),
                entity.isActive()
        );
    }
}
