package dev.sezrr.examples.llmchatservice.aimodel.internal.repository;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.modelPricing.ModelPricingQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.ModelPricing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModelPricingRepository extends ListCrudRepository<ModelPricing, UUID> {
    @Query("""
    SELECT p.id AS id,
           p.additionalPrice AS additionalPrice,
           p.details AS details,
           p.isActive AS active
    FROM ModelPricing p
    WHERE p.supportedModel.id IN :supportedModels AND p.isActive = true
    """)
    List<ModelPricingQueryDto> findActivePricingsBySupportedModels(@Param("supportedModels") List<UUID> supportedModelIds);

    List<ModelPricing> findBySupportedModelId(UUID supportedModelId);
}
