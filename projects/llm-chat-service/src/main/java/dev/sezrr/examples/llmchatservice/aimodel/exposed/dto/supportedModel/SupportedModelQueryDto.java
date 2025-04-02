package dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel;

import java.util.UUID;

public record SupportedModelQueryDto(UUID id, String model, String apiUrl) {

}
