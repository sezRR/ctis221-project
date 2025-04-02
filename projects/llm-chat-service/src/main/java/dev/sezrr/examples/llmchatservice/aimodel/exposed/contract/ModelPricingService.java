package dev.sezrr.examples.llmchatservice.aimodel.exposed.contract;

import dev.sezrr.examples.llmchatservice.aimodel.internal.model.ModelPricing;

import java.util.List;
import java.util.UUID;

public interface ModelPricingService {
    List<ModelPricing> getAdditionalPricings();
    ModelPricing getAdditionalPricingByModelId(UUID modelId);
    List<ModelPricing> getAdditionalPricingsByModelName(String modelName);
    List<ModelPricing> getAdditionalPricingsByApiUrl(String apiUrl);
    ModelPricing addAdditionalPricing(ModelPricing modelPricing);
}
