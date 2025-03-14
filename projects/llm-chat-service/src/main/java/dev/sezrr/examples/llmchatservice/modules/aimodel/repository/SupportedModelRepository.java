package dev.sezrr.examples.llmchatservice.modules.aimodel.repository;

import dev.sezrr.examples.llmchatservice.modules.aimodel.model.SupportedModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupportedModelRepository extends JpaRepository<SupportedModel, UUID>, JpaSpecificationExecutor<SupportedModel> {
    List<SupportedModel> findAllByApiUrl(String apiUrl);
    Optional<SupportedModel> findByModel(String model);
}
