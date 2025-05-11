package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.core.constants.SupportedModelConstants;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.AuditEntity;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.uuid7.UuidV7;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    
    @NotEmpty(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_MODEL_NAME_REQUIRED)
    @NotNull(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_MODEL_NAME_REQUIRED)
    @Column(nullable = false)
    private String model;

    @NotEmpty(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_API_URL_REQUIRED)
    @NotNull(message = SupportedModelConstants.VALIDATION_SUPPORTED_MODEL_API_URL_REQUIRED)
    @Column(name = "api_url", nullable = false)
    private String apiUrl; // TODO: API PROVIDERS -> baseApiUrl, endpoint, etc.
    
    @JsonManagedReference
    @OneToMany(mappedBy = "supportedModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModelPricing> pricings = new ArrayList<>();
}
