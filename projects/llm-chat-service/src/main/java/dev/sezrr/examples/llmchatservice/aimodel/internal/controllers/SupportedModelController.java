package dev.sezrr.examples.llmchatservice.aimodel.internal.controllers;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.SupportedModelQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.contracts.SupportedModelService;
import dev.sezrr.examples.llmchatservice.aimodel.internal.models.SupportedModel;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.shared.customresponseentities.CustomResponseEntity;
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
    public ResponseEntity<CustomResponseEntity<List<SupportedModel>>> getSupportedModels(
            @RequestParam(required = false) String apiUrl,
            @RequestParam(required = false) String model
    ) {
        if (apiUrl == null && model == null) {
            var supportedModels = supportedModelService.findAllCache();
            if (supportedModels.isEmpty())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            
            return ResponseEntity.ok(CustomResponseEntity.success(supportedModels));
        }
        
        var supportedModels = supportedModelService.getSupportedModels(apiUrl, model);
        if (supportedModels.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(supportedModels));
    }
    
    @GetMapping("/{model}")
    public ResponseEntity<CustomResponseEntity<List<SupportedModel>>> getSupportedModelsByModelName(@PathVariable String model) {
        var supportedModels = supportedModelService.getSupportedModelsByModelName(model);
        
        if (supportedModels.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(supportedModels));
    }
    
    @GetMapping("/api/{apiUrl}")
    public ResponseEntity<CustomResponseEntity<List<SupportedModel>>> getSupportedModelsByApiUrl(@PathVariable String apiUrl) {
        var supportedModels = supportedModelService.getSupportedModelsByApiUrl(apiUrl);

        if (supportedModels.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(CustomResponseEntity.success(supportedModels));
    }
    
    @PostMapping
    public ResponseEntity<CustomResponseEntity<SupportedModelQueryDto>> addSupportedModel(@RequestBody SupportedModelAddDto supportedModelAddDto) {
        var supportedModel = supportedModelService.addSupportedModel(supportedModelAddDto);

        if (supportedModel == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.ok(CustomResponseEntity.success(supportedModel));
    }
}
