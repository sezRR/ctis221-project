package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.model_pricing;

import java.io.Serializable;
import java.util.UUID;

public record ModelPricingQueryDto(
        UUID id,
        Double additionalPrice,
        String details,
        Boolean active
) implements Serializable {
}
