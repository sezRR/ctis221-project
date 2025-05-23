package dev.sezrr.llmchatwrapper.llmchatservice.instruction.internal.model;

import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.AuditEntity;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.uuid7.UuidV7;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseInstruction extends AuditEntity {
    @Id
    @UuidV7
    private UUID id;

    @UuidV7
    @NotNull(message = "Model ID cannot be null")
    private UUID modelId;
    
    @NotNull(message = "Instruction cannot be null")
    private String instruction;

    protected BaseInstruction() {
    }

    protected BaseInstruction(UUID modelId, String instruction) {
        super();
        this.modelId = modelId;
        this.instruction = instruction;
    }
}
