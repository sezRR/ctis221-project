package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.AuditEntity;
import dev.sezrr.llmchatwrapper.llmchatservice.shared.model.uuid7.UuidV7;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "model_pricings")
public class ModelPricing extends AuditEntity {
    @Id
    @UuidV7
    private UUID id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supported_model_id", nullable = false)
    private SupportedModel supportedModel;
    
    @Column(name = "additional_price")
    private double additionalPrice;
    
    private String details;
}
