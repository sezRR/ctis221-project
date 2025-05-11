package dev.sezrr.llmchatwrapper.llmchatservice.instruction.exposed.dto;

import java.util.UUID;

public record SystemInstructionAddDto(
        String instruction,
        UUID modelId
) {
}
