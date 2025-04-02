package dev.sezrr.examples.llmchatservice.aimodel.internal.service;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.contract.ModelPricingService;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.contract.SupportedModelService;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.ModelPricing;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.examples.llmchatservice.aimodel.internal.repository.ModelPricingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ModelPricingServiceImpl implements ModelPricingService {
    private final SupportedModelService supportedModelService;
    private final ModelPricingRepository modelPricingRepository;
    
    @Override
    public List<ModelPricing> getAdditionalPricings() {
        return modelPricingRepository.findAll();
    }

    @Override
    public ModelPricing getAdditionalPricingByModelId(UUID modelId) {
        return modelPricingRepository.findById(modelId)
                .orElse(null);
    }

    @Override
    public List<ModelPricing> getAdditionalPricingsByModelName(String modelName) {
        var supportedModels = supportedModelService.getSupportedModelsByModelName(modelName);
        supportedModelService.getSupportedModelsByModelName(modelName);

        return modelPricingRepository.getModelPricingBySupportedModel_IdIn(supportedModels
                .stream()
                .map(SupportedModel::getId)
                .toList());
    }

    @Override
    public List<ModelPricing> getAdditionalPricingsByApiUrl(String apiUrl) {
        var supportedModels = supportedModelService.getSupportedModelsByApiUrl(apiUrl);
        supportedModelService.getSupportedModelsByApiUrl(apiUrl);

        return modelPricingRepository.getModelPricingBySupportedModel_IdIn(supportedModels
                .stream()
                .map(SupportedModel::getId)
                .toList());
    }

    @Override
    public ModelPricing addAdditionalPricing(ModelPricing modelPricing) {
        return modelPricingRepository.save(modelPricing);
    }
}
