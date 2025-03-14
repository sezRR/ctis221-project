package dev.sezrr.examples.llmchatservice.modules.aimodel.repository.specifications;

import dev.sezrr.examples.llmchatservice.modules.aimodel.model.SupportedModel;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SupportedModelSpecification {
    public static Specification<SupportedModel> filterModels(String apiUrl, String model) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (apiUrl != null)
                predicates.add(criteriaBuilder.equal(root.get("apiUrl"), apiUrl));
            
            if (model != null)
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("model")), "%" + model.toLowerCase() + "%"));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
