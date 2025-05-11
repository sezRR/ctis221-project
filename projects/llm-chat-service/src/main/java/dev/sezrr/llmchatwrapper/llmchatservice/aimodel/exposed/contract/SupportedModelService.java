package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.contract;

import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.supported_model.SupportedModelAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.supported_model.SupportedModelQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.service.cache.CacheImplementationService;

import java.util.List;
import java.util.UUID;

public interface SupportedModelService extends CacheImplementationService<SupportedModelQueryDto> {
    List<SupportedModelQueryDto> getSupportedModels(String apiUrl, String model);
    
    SupportedModel getById(String id);
    boolean existsById(UUID id);
    
    SupportedModelQueryDto addSupportedModel(SupportedModelAddDto supportedModelAddDto);
    
    boolean deleteSupportedModel(UUID id);
}
