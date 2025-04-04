package dev.sezrr.examples.llmchatservice.aimodel.internal.repository;

import dev.sezrr.examples.llmchatservice.aimodel.internal.model.SupportedModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SupportedModelRepository extends JpaRepository<SupportedModel, UUID>, JpaSpecificationExecutor<SupportedModel> {
    boolean existsByModelAndApiUrl(String model, String apiUrl);
}
