package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model.mapper;

import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.supported_model.SupportedModelQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.AuditEntity;

public class SupportedModelDtoMapper {
    private SupportedModelDtoMapper() {
        // Private constructor to prevent instantiation
        throw new IllegalStateException("Mapper class cannot be instantiated");
    }
    
    public static SupportedModelQueryDto mapToDto(SupportedModel model) {
        return new SupportedModelQueryDto(
                model.getId(),
                model.getModel(),
                model.getApiUrl(),
                model.getPricings() != null ? model.getPricings()
                        .stream()
                        .filter(AuditEntity::isActive)
                        .map(ModelPricingDtoMapper::mapToDto)
                        .findFirst().orElse(null): null
        );
    }
}
