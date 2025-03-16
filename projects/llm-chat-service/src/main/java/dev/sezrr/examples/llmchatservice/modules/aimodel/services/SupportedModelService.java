package dev.sezrr.examples.llmchatservice.modules.aimodel.services;

import dev.sezrr.examples.llmchatservice.modules.aimodel.models.SupportedModel;
import dev.sezrr.examples.llmchatservice.modules.aimodel.models.dto.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.modules.aimodel.services.cache.CacheImplementationService;
import dev.sezrr.examples.llmchatservice.shared.customResponseEntities.CustomResponseEntity;

import java.util.List;

public interface SupportedModelService extends CacheImplementationService<SupportedModel> {
    CustomResponseEntity<List<SupportedModel>> getSupportedModels(String apiUrl, String model);
    CustomResponseEntity<List<SupportedModel>> getSupportedModelByModelName(String model);
    CustomResponseEntity<List<SupportedModel>> getSupportedModelByApiUrl(String apiUrl);
    CustomResponseEntity<SupportedModel> addSupportedModel(SupportedModelAddDto supportedModelAddDto);
}
