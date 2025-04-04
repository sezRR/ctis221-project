package dev.sezrr.examples.llmchatservice.aimodel.exposed.contract;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel.SupportedModelQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.examples.llmchatservice.aimodel.internal.service.cache.CacheImplementationService;

import java.util.List;
import java.util.Optional;

public interface SupportedModelService extends CacheImplementationService<SupportedModelQueryDto> {
    List<SupportedModelQueryDto> getSupportedModels(String apiUrl, String model);

    SupportedModel getById(String id);
    
    SupportedModelQueryDto addSupportedModel(SupportedModelAddDto supportedModelAddDto);
}
