package dev.sezrr.examples.llmchatservice.aimodel.exposed.dto;

import java.util.UUID;

public record SupportedModelQueryDto(UUID id, String model, String apiUrl) {

}
