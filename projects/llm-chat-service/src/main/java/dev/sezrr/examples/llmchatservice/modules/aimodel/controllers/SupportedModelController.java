package dev.sezrr.examples.llmchatservice.modules.aimodel.controllers;

import dev.sezrr.examples.llmchatservice.modules.aimodel.services.SupportedModelService;
import dev.sezrr.examples.llmchatservice.modules.aimodel.models.SupportedModel;
import dev.sezrr.examples.llmchatservice.modules.aimodel.models.dto.SupportedModelAddDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/api/v1/models"))
public class SupportedModelController {
    private final SupportedModelService supportedModelService;
    
    @GetMapping
    public ResponseEntity<List<SupportedModel>> getSupportedModels(
            @RequestParam(required = false) String apiUrl,
            @RequestParam(required = false) String model
    ) {
        var supportedModels = supportedModelService.getSupportedModels(apiUrl, model);
        if (!supportedModels.isSuccess())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(supportedModels.getData());
    }
    
    @GetMapping("/{model}")
    public ResponseEntity<SupportedModel> getSupportedModelByModelName(@PathVariable String model) {
        var supportedModel = supportedModelService.getSupportedModelByModelName(model);
        
        if (!supportedModel.isSuccess())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(supportedModel.getData());
    }
    
    @PostMapping
    public ResponseEntity<SupportedModel> addSupportedModel(@RequestBody @Valid SupportedModelAddDto supportedModelAddDto) {
        var supportedModel = supportedModelService.addSupportedModel(supportedModelAddDto);
        
        if (!supportedModel.isSuccess())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(supportedModel.getData());
    }
}
