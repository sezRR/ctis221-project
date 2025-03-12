package dev.sezrr.examples.llmchatservice.modules.aimodel.model;

import dev.sezrr.examples.llmchatservice.shared.model.AuditEntity;
import dev.sezrr.examples.llmchatservice.shared.model.uuid7.UuidV7;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "model_pricings")
public class ModelPricing extends AuditEntity {
    @Id
    @UuidV7
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "supported_model_id", nullable = false)
    private SupportedModel supportedModel;
    
    @Column(name = "additional_price")
    private double additionalPrice;
    
    private String details;
}
