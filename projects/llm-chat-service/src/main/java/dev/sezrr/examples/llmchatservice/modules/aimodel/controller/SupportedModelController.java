package dev.sezrr.examples.llmchatservice.modules.aimodel.controller;

import dev.sezrr.examples.llmchatservice.modules.aimodel.model.SupportedModel;
import dev.sezrr.examples.llmchatservice.modules.aimodel.model.dto.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.modules.aimodel.repository.SupportedModelRepository;
import dev.sezrr.examples.llmchatservice.modules.aimodel.repository.specifications.SupportedModelSpecification;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/api/v1/models"))
public class SupportedModelController {
    private final SupportedModelRepository supportedModelRepository;

    @Autowired
    public SupportedModelController(SupportedModelRepository supportedModelRepository) {
        this.supportedModelRepository = supportedModelRepository;
    }

    @GetMapping
    public ResponseEntity<List<SupportedModel>> getSupportedModels(
            @RequestParam(required = false) String apiUrl,
            @RequestParam(required = false) String model
    ) {
        Specification<SupportedModel> spec = SupportedModelSpecification.filterModels(apiUrl, model);
        return ResponseEntity.ok(supportedModelRepository.findAll(spec));
    }
    
    @GetMapping("/{model}")
    public ResponseEntity<SupportedModel> getSupportedModelByModelName(@PathVariable String model) {
        var supportedModel = supportedModelRepository.findByModel(model);
        
        if (supportedModel.isPresent())
            return ResponseEntity.ok(supportedModel.get());
        
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    
    @PostMapping
    public ResponseEntity<SupportedModel> addSupportedModel(@RequestBody @Valid SupportedModelAddDto supportedModelAddDto) {
        SupportedModel supportedModel = new SupportedModel();
        supportedModel.setModel(supportedModelAddDto.getModel());
        supportedModel.setApiUrl(supportedModelAddDto.getApiUrl());
        
        supportedModelRepository.save(supportedModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(supportedModel);
    }
}
