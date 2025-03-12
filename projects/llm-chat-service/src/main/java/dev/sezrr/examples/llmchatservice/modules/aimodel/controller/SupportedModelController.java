package dev.sezrr.examples.llmchatservice.modules.aimodel.controller;

import dev.sezrr.examples.llmchatservice.modules.aimodel.model.SupportedModel;
import dev.sezrr.examples.llmchatservice.modules.aimodel.repository.SupportedModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/api/v1/supported-model"))
public class SupportedModelController {
    private final SupportedModelRepository supportedModelRepository;

    @Autowired
    public SupportedModelController(SupportedModelRepository supportedModelRepository) {
        this.supportedModelRepository = supportedModelRepository;
    }

    @GetMapping
    public Iterable<SupportedModel> getSupportedModels() {
        return supportedModelRepository.findAllBy();
    }
    
    @PostMapping
    public SupportedModel addSupportedModel(@RequestBody SupportedModel supportedModel) {
        return supportedModelRepository.save(supportedModel);
    }
}
