package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.service;

import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.supported_model.SupportedModelQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.contract.SupportedModelService;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.supported_model.SupportedModelAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model.mapper.SupportedModelDtoMapper;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.repository.SupportedModelRepository;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.repository.specifications.SupportedModelSpecification;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.core.constants.SupportedModelConstants;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.exception.ConflictException;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.validation.CustomValidatorHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
    public List<SupportedModelQueryDto> findAllCache() {
        return supportedModelRepository.findAll()
                .stream()
                .map(SupportedModelDtoMapper::mapToDto)
                .toList();
    }

    @Override
    public List<SupportedModelQueryDto> getSupportedModels(String apiUrl, String model) {
        Specification<SupportedModel> spec = SupportedModelSpecification.filterModels(apiUrl, model);
        return supportedModelRepository.findAll(spec).stream().map(SupportedModelDtoMapper::mapToDto).toList();
    }

    @Override
    public SupportedModel getById(String id) {
        return supportedModelRepository.findById(UUID.fromString(id)).orElse(null);
    }

    @Override
    public boolean existsById(UUID id) {
        return supportedModelRepository.existsById(id);
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
        return SupportedModelDtoMapper.mapToDto(savedModel);
    }

    @CacheEvict(value = SupportedModelConstants.SUPPORTED_MODEL_CACHE_NAME, allEntries = true)
    @Override
    public boolean deleteSupportedModel(UUID id) {
        if (supportedModelRepository.existsById(id)) {
            supportedModelRepository.deleteById(id);
            return true;
        }
        return false; 
    }
}
