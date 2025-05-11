package dev.sezrr.llmchatwrapper.llmchatservice.instruction.exposed.contract;

import dev.sezrr.llmchatwrapper.llmchatservice.instruction.exposed.dto.SystemInstructionAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.instruction.internal.model.SystemInstruction;

import java.util.UUID;

public interface SystemInstructionService {
    SystemInstruction getSystemInstructionByModelId(UUID modelId);

    SystemInstruction getSystemInstructionById(UUID id);

    SystemInstruction createSystemInstruction(SystemInstructionAddDto systemInstructionAddDto);
}
