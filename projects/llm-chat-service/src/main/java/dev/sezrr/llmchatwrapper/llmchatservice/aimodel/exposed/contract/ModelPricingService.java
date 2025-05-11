package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.contract;

import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingQueryDto;

import java.util.List;
import java.util.UUID;

public interface ModelPricingService {
    List<ModelPricingQueryDto> getAdditionalPricings();
    List<ModelPricingQueryDto> filterActiveAdditionalPricings(String modelName, String apiUrl);

    ModelPricingQueryDto addAdditionalPricing(ModelPricingAddDto modelPricing);
    ModelPricingQueryDto activatePricing(UUID pricingId);
    ModelPricingQueryDto deactivatePricing(UUID pricingId);
}
