package dev.sezrr.examples.llmchatservice.modules.aimodel.models.dto;

import dev.sezrr.examples.llmchatservice.modules.aimodel.core.constants.SupportedModelConstants;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SupportedModelAddDto {
    @NotNull(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_MODEL_NAME_REQUIRED)
    private String model;
    @NotNull(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_API_URL_REQUIRED)
    private String apiUrl;
}
