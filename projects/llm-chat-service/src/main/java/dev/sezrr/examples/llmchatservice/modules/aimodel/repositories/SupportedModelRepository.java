package dev.sezrr.examples.llmchatservice.modules.aimodel.repositories;

import dev.sezrr.examples.llmchatservice.modules.aimodel.models.SupportedModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SupportedModelRepository extends JpaRepository<SupportedModel, UUID>, JpaSpecificationExecutor<SupportedModel> {
    List<SupportedModel> findAllByApiUrl(String apiUrl);
    List<SupportedModel> findAllByModel(String model);
}
