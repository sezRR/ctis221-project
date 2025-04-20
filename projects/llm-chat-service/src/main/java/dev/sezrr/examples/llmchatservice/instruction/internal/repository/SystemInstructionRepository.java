package dev.sezrr.examples.llmchatservice.instruction.internal.repository;

import dev.sezrr.examples.llmchatservice.instruction.internal.model.SystemInstruction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SystemInstructionRepository extends CrudRepository<SystemInstruction, UUID> {
    SystemInstruction findByModelId(UUID modelId);
}
