package dev.sezrr.examples.llmchatservice.modules.aimodel.controllers;

import dev.sezrr.examples.llmchatservice.modules.aimodel.services.SupportedModelService;
import dev.sezrr.examples.llmchatservice.modules.aimodel.models.SupportedModel;
import dev.sezrr.examples.llmchatservice.modules.aimodel.models.dto.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.shared.customResponseEntities.CustomResponseEntity;
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
        if (!supportedModels.isSuccess())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(supportedModels);
    }
    
    @GetMapping("/{model}")
    public ResponseEntity<CustomResponseEntity<List<SupportedModel>>> getSupportedModelsByModelName(@PathVariable String model) {
        var supportedModel = supportedModelService.getSupportedModelByModelName(model);
        
        if (!supportedModel.isSuccess())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(supportedModel);
    }
    
    @GetMapping("/api/{apiUrl}")
    public ResponseEntity<CustomResponseEntity<List<SupportedModel>>> getSupportedModelsByApiUrl(@PathVariable String apiUrl) {
        var supportedModel = supportedModelService.getSupportedModelByApiUrl(apiUrl);
        
        if (!supportedModel.isSuccess())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(supportedModel);
    }
    
    @PostMapping
    public ResponseEntity<CustomResponseEntity<SupportedModel>> addSupportedModel(@RequestBody @Valid SupportedModelAddDto supportedModelAddDto) {
        var supportedModel = supportedModelService.addSupportedModel(supportedModelAddDto);
        
        if (!supportedModel.isSuccess())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(supportedModel);
    }
}
