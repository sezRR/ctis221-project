package dev.sezrr.examples.llmchatservice.aimodel.internal.model.mapper;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel.SupportedModelQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.examples.llmchatservice.shared.model.AuditEntity;

public class SupportedModelDtoMapper {
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
