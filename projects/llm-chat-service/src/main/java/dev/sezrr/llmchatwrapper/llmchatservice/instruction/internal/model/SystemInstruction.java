package dev.sezrr.llmchatwrapper.llmchatservice.instruction.internal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "system_instructions")
public class SystemInstruction extends BaseInstruction {
    public SystemInstruction(UUID modelId, String instruction) {
        super(modelId, instruction);
    }

    public SystemInstruction() {
        
    }
}
