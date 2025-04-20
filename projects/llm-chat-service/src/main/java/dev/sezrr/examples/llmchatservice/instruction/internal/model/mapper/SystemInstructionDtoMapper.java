package dev.sezrr.examples.llmchatservice.instruction.internal.model.mapper;

import dev.sezrr.examples.llmchatservice.instruction.exposed.dto.SystemInstructionAddDto;
import dev.sezrr.examples.llmchatservice.instruction.internal.model.SystemInstruction;

public class SystemInstructionDtoMapper {
    private SystemInstructionDtoMapper() {
        // Prevent instantiation
        throw new IllegalStateException("Mapper class cannot be instantiated");
    }
    
    public static SystemInstruction mapToDto(SystemInstructionAddDto systemInstructionAddDto) {
        return new SystemInstruction(
                systemInstructionAddDto.modelId(),
                systemInstructionAddDto.instruction()
                );
    }
}
