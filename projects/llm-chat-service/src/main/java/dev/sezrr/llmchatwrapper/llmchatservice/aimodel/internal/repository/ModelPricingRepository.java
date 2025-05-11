package dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.repository;

import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingQueryDto;
import dev.sezrr.llmchatwrapper.llmchatservice.aimodel.internal.model.ModelPricing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModelPricingRepository extends ListCrudRepository<ModelPricing, UUID> {
    @Query("""
            SELECT new dev.sezrr.llmchatwrapper.llmchatservice.aimodel.exposed.dto.model_pricing.ModelPricingQueryDto(
                p.id, p.additionalPrice, p.details, p.isActive)
            FROM ModelPricing p
            WHERE p.supportedModel.id IN :supportedModels AND p.isActive = true
            """)
    List<ModelPricingQueryDto> findActivePricingsBySupportedModels(@Param("supportedModels") List<UUID> supportedModelIds);

    List<ModelPricing> findBySupportedModelId(UUID supportedModelId);
}
