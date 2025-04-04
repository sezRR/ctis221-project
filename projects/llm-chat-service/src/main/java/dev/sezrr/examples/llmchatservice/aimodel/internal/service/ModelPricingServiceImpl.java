package dev.sezrr.examples.llmchatservice.aimodel.internal.service;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.contract.ModelPricingService;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.contract.SupportedModelService;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingAddDto;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supported_model.SupportedModelQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.internal.core.constants.SupportedModelConstants;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.ModelPricing;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.mapper.ModelPricingDtoMapper;
import dev.sezrr.examples.llmchatservice.aimodel.internal.repository.ModelPricingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ModelPricingServiceImpl implements ModelPricingService {
    private final ModelPricingRepository modelPricingRepository;
    private final SupportedModelService supportedModelService;

    @Override
    public List<ModelPricingQueryDto> getAdditionalPricings() {
        return modelPricingRepository.findAll()
                .stream()
                .map(ModelPricingDtoMapper::mapToDto)
                .toList();
    }

    @Override
    public List<ModelPricingQueryDto> filterActiveAdditionalPricings(String modelName, String apiUrl) {
        List<UUID> supportedModelIds = supportedModelService.getSupportedModels(apiUrl, modelName)
                .stream()
                .map(SupportedModelQueryDto::id)
                .toList();

        if (supportedModelIds.isEmpty())
            return Collections.emptyList();

        return modelPricingRepository.findActivePricingsBySupportedModels(supportedModelIds);
    }

    @CacheEvict(value = SupportedModelConstants.SUPPORTED_MODEL_CACHE_NAME, allEntries = true)
    @Override
    public ModelPricingQueryDto addAdditionalPricing(ModelPricingAddDto modelPricingAddDto) {
        SupportedModel supportedModel = supportedModelService.getById(modelPricingAddDto.modelId());
        if (supportedModel == null)
            return null;

        if (modelPricingAddDto.active())
            deactivateAllPricingsForModel(supportedModel.getId());

        ModelPricing newPricing = new ModelPricing();
        newPricing.setAdditionalPrice(modelPricingAddDto.additionalPrice());
        newPricing.setDetails(modelPricingAddDto.details());
        newPricing.setActive(modelPricingAddDto.active());
        newPricing.setSupportedModel(supportedModel);

        ModelPricing saved = modelPricingRepository.save(newPricing);
        return ModelPricingDtoMapper.mapToDto(saved);
    }

    @CacheEvict(value = SupportedModelConstants.SUPPORTED_MODEL_CACHE_NAME, allEntries = true)
    @Override
    public ModelPricingQueryDto activatePricing(UUID pricingId) {
        ModelPricing pricingToActivate = modelPricingRepository.findById(pricingId)
                .orElse(null);

        assert pricingToActivate != null;
        SupportedModel model = pricingToActivate.getSupportedModel();
        deactivateAllPricingsForModel(model.getId());

        pricingToActivate.setActive(true);
        modelPricingRepository.save(pricingToActivate);

        return ModelPricingDtoMapper.mapToDto(pricingToActivate);
    }

    @Override
    public ModelPricingQueryDto deactivatePricing(UUID pricingId) {
        ModelPricing pricingToDeactivate = modelPricingRepository.findById(pricingId)
                .orElse(null);

        assert pricingToDeactivate != null;
        pricingToDeactivate.setActive(false);
        modelPricingRepository.save(pricingToDeactivate);

        return ModelPricingDtoMapper.mapToDto(pricingToDeactivate);
    }

    private void deactivateAllPricingsForModel(UUID modelId) {
        List<ModelPricing> existing = modelPricingRepository.findBySupportedModelId(modelId);

        for (ModelPricing pricing : existing)
            if (pricing.isActive())
                pricing.setActive(false);

        modelPricingRepository.saveAll(existing);
    }
}
