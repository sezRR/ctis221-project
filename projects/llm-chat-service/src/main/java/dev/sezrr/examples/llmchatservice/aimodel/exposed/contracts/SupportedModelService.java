package dev.sezrr.examples.llmchatservice.aimodel.exposed.contracts;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.SupportedModelQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.internal.models.SupportedModel;
import dev.sezrr.examples.llmchatservice.aimodel.internal.services.cache.CacheImplementationService;

import java.util.List;

public interface SupportedModelService extends CacheImplementationService<SupportedModel> {
    List<SupportedModel> getSupportedModels(String apiUrl, String model);
    List<SupportedModel> getSupportedModelsByModelName(String model);
    List<SupportedModel> getSupportedModelsByApiUrl(String apiUrl);
    SupportedModelQueryDto addSupportedModel(SupportedModelAddDto supportedModelAddDto);
}
