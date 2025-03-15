package dev.sezrr.examples.llmchatservice.modules.aimodel.models.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SupportedModelAddDto {
    @NotNull
    private String model;
    @NotNull
    private String apiUrl;
}
