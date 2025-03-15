package dev.sezrr.examples.llmchatservice.modules.aimodel.services;

import dev.sezrr.examples.llmchatservice.modules.aimodel.models.SupportedModel;
import dev.sezrr.examples.llmchatservice.modules.aimodel.models.dto.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.shared.results.Result;

import java.util.List;

public interface SupportedModelService {
    Result<List<SupportedModel>> getSupportedModels(String apiUrl, String model);
    Result<SupportedModel> getSupportedModelByModelName(String model);
    Result<SupportedModel> addSupportedModel(SupportedModelAddDto supportedModelAddDto);
}
