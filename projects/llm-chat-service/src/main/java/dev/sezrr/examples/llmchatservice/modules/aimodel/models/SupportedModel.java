package dev.sezrr.examples.llmchatservice.modules.aimodel.models;

import dev.sezrr.examples.llmchatservice.modules.aimodel.core.constants.SupportedModelConstants;
import dev.sezrr.examples.llmchatservice.shared.models.AuditEntity;
import dev.sezrr.examples.llmchatservice.shared.models.uuid7.UuidV7;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "supported_models")
public class SupportedModel extends AuditEntity implements Serializable {
    @Id
    @UuidV7
    private UUID id;
    
    @NotNull(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_MODEL_NAME_REQUIRED)
    @Column(nullable = false)
    private String model;

    @NotNull(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_API_URL_REQUIRED)
    @Column(name = "api_url", nullable = false)
    private String apiUrl;
}
