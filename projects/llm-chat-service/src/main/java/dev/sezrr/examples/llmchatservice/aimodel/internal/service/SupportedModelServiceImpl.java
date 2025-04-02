package dev.sezrr.examples.llmchatservice.aimodel.internal.service;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel.SupportedModelQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.contract.SupportedModelService;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.aimodel.internal.repository.SupportedModelRepository;
import dev.sezrr.examples.llmchatservice.aimodel.internal.repository.specifications.SupportedModelSpecification;
import dev.sezrr.examples.llmchatservice.aimodel.internal.core.constants.SupportedModelConstants;
import dev.sezrr.examples.llmchatservice.shared.exception.ConflictException;
import dev.sezrr.examples.llmchatservice.shared.validation.CustomValidatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportedModelServiceImpl implements SupportedModelService {
    private final SupportedModelRepository supportedModelRepository;
    private final CustomValidatorHelper customValidatorHelper;

    @Autowired
    public SupportedModelServiceImpl(SupportedModelRepository supportedModelRepository, CustomValidatorHelper customValidatorHelper) {
        this.supportedModelRepository = supportedModelRepository;
        this.customValidatorHelper = customValidatorHelper;
    }

    @Cacheable(value = SupportedModelConstants.SUPPORTED_MODEL_CACHE_NAME)
    @Override
    public List<SupportedModel> findAllCache() {
        return supportedModelRepository.findAll();
    }

    @Override
    public List<SupportedModel> getSupportedModels(String apiUrl, String model) {
        Specification<SupportedModel> spec = SupportedModelSpecification.filterModels(apiUrl, model);
        return supportedModelRepository.findAll(spec);
    }

    @Override
    public List<SupportedModel> getSupportedModelsByModelName(String model) {
        return supportedModelRepository.findAllByModel(model);
    }

    @Override
    public List<SupportedModel> getSupportedModelsByApiUrl(String apiUrl) {
        return supportedModelRepository.findAllByApiUrl(apiUrl);
    }

    @CacheEvict(value = SupportedModelConstants.SUPPORTED_MODEL_CACHE_NAME, allEntries = true)
    @Override
    public SupportedModelQueryDto addSupportedModel(SupportedModelAddDto supportedModelAddDto) {
        SupportedModel supportedModel = new SupportedModel();
        supportedModel.setModel(supportedModelAddDto.model());
        supportedModel.setApiUrl(supportedModelAddDto.apiUrl());
        supportedModel.setActive(true);

        customValidatorHelper.validateOrThrow(supportedModel, "Validation failed due to incorrect input values.");
        
        if (supportedModelRepository.existsByModelAndApiUrl(supportedModel.getModel(), supportedModel.getApiUrl()))
            throw new ConflictException("The model with the same name and API URL already exists.");
        
        var savedModel = supportedModelRepository.save(supportedModel);
        return new SupportedModelQueryDto(savedModel.getId(), savedModel.getModel(), savedModel.getApiUrl());
    }
}
