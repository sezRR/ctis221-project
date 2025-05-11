package dev.sezrr.llmchatwrapper.llmchatservice.instruction.internal.service;

import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.contract.SupportedModelService;
import dev.sezrr.llmchatwrapper.llmchatservice.instruction.exposed.contract.SystemInstructionService;
import dev.sezrr.llmchatwrapper.llmchatservice.instruction.exposed.dto.SystemInstructionAddDto;
import dev.sezrr.llmchatwrapper.llmchatservice.instruction.internal.model.SystemInstruction;
import dev.sezrr.llmchatwrapper.llmchatservice.instruction.internal.model.mapper.SystemInstructionDtoMapper;
import dev.sezrr.llmchatwrapper.llmchatservice.instruction.internal.repository.SystemInstructionRepository;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.validation.CustomValidatorHelper;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.validation.ValidationConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SystemInstructionServiceImpl implements SystemInstructionService {
    private final SystemInstructionRepository systemInstructionRepository;
    private final CustomValidatorHelper customValidatorHelper;
    
    private final SupportedModelService supportedModelService;
    
    @Override
    public SystemInstruction getSystemInstructionByModelId(UUID modelId) {
        return systemInstructionRepository.findByModelId(modelId);
    }

    @Override
    public SystemInstruction getSystemInstructionById(UUID id) {
        return systemInstructionRepository.findById(id).orElse(null);
    }

    @Override
    public SystemInstruction createSystemInstruction(SystemInstructionAddDto systemInstructionAddDto) {
        var isExists = supportedModelService.existsById(systemInstructionAddDto.modelId());
        if (!isExists) {
            throw new IllegalArgumentException("Model with ID " + systemInstructionAddDto.modelId() + " does not exist.");
        }
        
        SystemInstruction systemInstruction = SystemInstructionDtoMapper.mapToDto(systemInstructionAddDto);
        customValidatorHelper.validateOrThrow(systemInstruction, ValidationConstant.VALIDATION_FAILED);
        
        return systemInstructionRepository.save(systemInstruction);
    }
}
