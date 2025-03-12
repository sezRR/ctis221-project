package dev.sezrr.examples.llmchatservice.modules.aimodel.repository;

import dev.sezrr.examples.llmchatservice.modules.aimodel.model.SupportedModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupportedModelRepository extends CrudRepository<SupportedModel, UUID> {
    Iterable<SupportedModel> findAllBy();
    Optional<SupportedModel> findByApiUrl(String apiUrl);
    Optional<SupportedModel> findByModel(String model);
}
