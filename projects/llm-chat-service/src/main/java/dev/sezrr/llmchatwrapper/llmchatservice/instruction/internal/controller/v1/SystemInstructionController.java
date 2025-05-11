package dev.sezrr.llmchatwrapper.llmchatservice.instruction.internal.controller.v1;

import dev.sezrr.llmchatwrapper.llmchatservice.instruction.exposed.contract.SystemInstructionService;
import dev.sezrr.llmchatwrapper.llmchatservice.instruction.exposed.dto.SystemInstructionAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.instruction.internal.model.SystemInstruction;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/instructions/system")
public class SystemInstructionController {
    private final SystemInstructionService systemInstructionService;
    
    @GetMapping("/{id}")
    public SystemInstruction getSystemInstructionById(@PathVariable UUID id) {
        return systemInstructionService.getSystemInstructionById(id);
    }

    @GetMapping("/model/{modelId}")
    public SystemInstruction getSystemInstructionByModelId(@PathVariable UUID modelId) {
        return systemInstructionService.getSystemInstructionByModelId(modelId);
    }

    @PostMapping
    public SystemInstruction createSystemInstruction(@RequestBody @Valid SystemInstructionAddDto systemInstructionAddDto) {
        return systemInstructionService.createSystemInstruction(systemInstructionAddDto);
    }
}
