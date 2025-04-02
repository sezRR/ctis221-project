package dev.sezrr.examples.llmchatservice.aimodel.exposed.contract;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel.SupportedModelQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.examples.llmchatservice.aimodel.internal.service.cache.CacheImplementationService;

import java.util.List;

public interface SupportedModelService extends CacheImplementationService<SupportedModel> {
    List<SupportedModel> getSupportedModels(String apiUrl, String model);
    List<SupportedModel> getSupportedModelsByModelName(String model);
    List<SupportedModel> getSupportedModelsByApiUrl(String apiUrl);
    SupportedModelQueryDto addSupportedModel(SupportedModelAddDto supportedModelAddDto);
}
