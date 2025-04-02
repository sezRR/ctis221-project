package dev.sezrr.examples.llmchatservice.aimodel.internal.repository;

import dev.sezrr.examples.llmchatservice.aimodel.internal.model.ModelPricing;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ModelPricingRepository extends ListCrudRepository<ModelPricing, UUID> {
    ModelPricing getModelPricingBySupportedModel_Id(UUID modelId);
    List<ModelPricing> getModelPricingBySupportedModel_IdIn(List<UUID> modelIds);
}
