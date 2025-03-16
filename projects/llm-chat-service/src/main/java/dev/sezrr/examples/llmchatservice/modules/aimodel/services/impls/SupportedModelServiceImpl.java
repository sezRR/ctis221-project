package dev.sezrr.examples.llmchatservice.modules.aimodel.services.impls;

import dev.sezrr.examples.llmchatservice.modules.aimodel.services.SupportedModelService;
import dev.sezrr.examples.llmchatservice.modules.aimodel.models.SupportedModel;
import dev.sezrr.examples.llmchatservice.modules.aimodel.models.dto.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.modules.aimodel.repositories.SupportedModelRepository;
import dev.sezrr.examples.llmchatservice.modules.aimodel.repositories.specifications.SupportedModelSpecification;
import dev.sezrr.examples.llmchatservice.modules.aimodel.core.constants.SupportedModelConstants;
import dev.sezrr.examples.llmchatservice.shared.customResponseEntities.CustomResponseEntity;
import dev.sezrr.examples.llmchatservice.shared.validation.ValidationResponseChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportedModelServiceImpl implements SupportedModelService {
    private final SupportedModelRepository supportedModelRepository;
    private final ValidationResponseChecker validationResponseChecker;

    @Autowired
    public SupportedModelServiceImpl(SupportedModelRepository supportedModelRepository, ValidationResponseChecker validationResponseChecker) {
        this.supportedModelRepository = supportedModelRepository;
        this.validationResponseChecker = validationResponseChecker;
    }

    @Cacheable(value = SupportedModelConstants.SUPPORTED_MODEL_CACHE_NAME)
    @Override
    public List<SupportedModel> findAllCache() {
        return supportedModelRepository.findAll();
    }

    @Override
    public CustomResponseEntity<List<SupportedModel>> getSupportedModels(String apiUrl, String model) {
        Specification<SupportedModel> spec = SupportedModelSpecification.filterModels(apiUrl, model);
        
        var supportedModels = supportedModelRepository.findAll(spec);
        if (supportedModels.isEmpty())
            return CustomResponseEntity.failureWithData("Models not found");
        
        return CustomResponseEntity.success(supportedModels);
    }

    @Override
    public CustomResponseEntity<List<SupportedModel>> getSupportedModelByModelName(String model) {
        var supportedModel = supportedModelRepository.findAllByModel(model);
        if (supportedModel.isEmpty())
            return CustomResponseEntity.failureWithData("Model not found");
        
        return CustomResponseEntity.success(supportedModel);
    }

    @Override
    public CustomResponseEntity<List<SupportedModel>> getSupportedModelByApiUrl(String apiUrl) {
        var supportedModel = supportedModelRepository.findAllByApiUrl(apiUrl);
        if (supportedModel.isEmpty())
            return CustomResponseEntity.failureWithData("Models not found");
        
        return CustomResponseEntity.success(supportedModel);
    }

    @CacheEvict(value = SupportedModelConstants.SUPPORTED_MODEL_CACHE_NAME, allEntries = true)
    @Override
    public CustomResponseEntity<SupportedModel> addSupportedModel(SupportedModelAddDto supportedModelAddDto) {
        SupportedModel supportedModel = new SupportedModel();
        supportedModel.setModel(supportedModelAddDto.getModel());
        supportedModel.setApiUrl(supportedModelAddDto.getApiUrl());

        var validationResult = validationResponseChecker.validateObject(supportedModel);
        if (!validationResult.isValid())
            return CustomResponseEntity.failureWithData(validationResult.getMessages().toString());

        supportedModelRepository.save(supportedModel);
        return CustomResponseEntity.success(supportedModel);
    }
}
