package dev.sezrr.examples.llmchatservice.aimodel.internal.controller.v1;

import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel.SupportedModelQueryDto;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.contract.SupportedModelService;
import dev.sezrr.examples.llmchatservice.aimodel.internal.model.SupportedModel;
import dev.sezrr.examples.llmchatservice.aimodel.exposed.dto.supportedModel.SupportedModelAddDto;
import dev.sezrr.examples.llmchatservice.shared.customresponseentity.CustomResponseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/models")
public class SupportedModelController {
    private final SupportedModelService supportedModelService;
    
    @GetMapping
    public ResponseEntity<CustomResponseEntity<List<SupportedModel>>> getSupportedModels(
            @RequestParam(required = false) String apiUrl,
            @RequestParam(required = false) String model
    ) {
        List<SupportedModel> supportedModels;
        if (apiUrl == null && model == null)
            supportedModels = supportedModelService.findAllCache();
        else        
            supportedModels = supportedModelService.getSupportedModels(apiUrl, model);
        
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
