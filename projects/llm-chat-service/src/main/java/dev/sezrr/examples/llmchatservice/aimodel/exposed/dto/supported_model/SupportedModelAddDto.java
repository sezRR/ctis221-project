package dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supported_model;

import dev.sezrr.examples.llmchatservice.aimodel.internal.core.constants.SupportedModelConstants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SupportedModelAddDto (
    @NotEmpty(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_MODEL_NAME_REQUIRED)
    @NotNull(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_MODEL_NAME_REQUIRED)
    String model,
    
    @NotEmpty(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_API_URL_REQUIRED)
    @NotNull(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_API_URL_REQUIRED)
    String apiUrl
) {

}