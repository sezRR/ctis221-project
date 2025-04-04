package dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.modelPricing;

import java.io.Serializable;
import java.util.UUID;

public record ModelPricingQueryDto(
        UUID id,
        Double additionalPrice,
        String details,
        Boolean active
) implements Serializable {
}
